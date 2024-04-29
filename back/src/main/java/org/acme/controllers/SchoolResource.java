package org.acme.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.acme.entities.School;
import org.acme.pagination.PagedResult;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Objects;


@Path("school")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SchoolResource {

    @Inject
    EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(SchoolResource.class.getName());


    @GET
    public Response listAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("sort") String sort
    ) {
        Long totalCount = School.count();
        size = (size == null) ? 10 : size;
        page = (page == null) ? 0 : page;
        sort = (Objects.equals(sort, "")) ? "schoolName" : sort;

        return Response.ok(new PagedResult<>(School.findAll(Sort.by((sort == null) ? "schoolName" : sort))
                .page(Page.of((page == null) ? 0 : page, (size == null) ? 10 : size))
                .list(),
                totalCount,
                (int) Math.ceil((double) totalCount / size),
                page > 0,
                (page + 1) * size < totalCount)).build();
    }


    @GET
    @Path("{schoolName}")
    public List<School> findByNameContaining(@QueryParam("schoolName") String schoolName) {
        return School.find("school_name LIKE ?1", "%" + schoolName + "%").list();
    }

    @POST
    @Transactional
    public Response create(School entity) {
        if (entity.id != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }

        entity.persist();
        return Response.ok(entity).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public School update(Long id, School entity_) {
        if (entity_.schoolName == null) {
          throw new WebApplicationException("School Name was not set on request.", 422);
        }

        School entity = School.findById(id);

        if (entity == null) {
            throw new WebApplicationException("School with id of " + id + " does not exist.", 404);
        }

        entity.schoolName = entity_.schoolName;
        entity.address = entity_.address;
        entity.phoneNumber = entity_.phoneNumber;

        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(Long id) {
        School entity = School.findById(id);
        if (entity == null) {
            throw new WebApplicationException("School with id of " + id + " does not exist.", 404);
        }
        entity.delete();
        return Response.status(204).build();
    }

    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Inject
        ObjectMapper objectMapper;

        @Override
        public Response toResponse(Exception exception) {
            LOGGER.error("Failed to handle request", exception);

            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }

            ObjectNode exceptionJson = objectMapper.createObjectNode();
            exceptionJson.put("exceptionType", exception.getClass().getName());
            exceptionJson.put("code", code);

            if (exception.getMessage() != null) {
                exceptionJson.put("error", exception.getMessage());
            }

            return Response.status(code)
                    .entity(exceptionJson)
                    .build();
        }

    }
}
