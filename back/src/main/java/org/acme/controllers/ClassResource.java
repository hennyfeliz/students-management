package org.acme.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.acme.entities.Class;
import org.acme.pagination.PagedResult;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Objects;

@Path("class")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClassResource {

    private static final Logger LOGGER = Logger.getLogger(ClassResource.class.getName());

    @GET
    public Response listAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("sort") String sort
    ) {
        Long totalCount = Class.count();
        size = (size == null) ? 10 : size;
        page = (page == null) ? 0 : page;
        sort = (Objects.equals(sort, "")) ? "className" : sort;

        return Response.ok(new PagedResult<>(Class.findAll(Sort.by((sort == null) ? "className" : sort))
                .page(Page.of((page == null) ? 0 : page, (size == null) ? 10 : size))
                .list(),
                totalCount,
                (int) Math.ceil((double) totalCount / size),
                page > 0,
                (page + 1) * size < totalCount)).build();
    }


    @Path("{id}")
    public Class getSingle(Long id) {
        Class entity = Class.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Class with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    @POST
    @Transactional
    public Response create(Class entity) {
        if (entity.id != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }

        entity.persist();
        return Response.ok(entity).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Class update(Long id, Class entity_) {
        if (entity_.className == null) {
            throw new WebApplicationException("Class Name was not set on request.", 422);
        }

        Class entity = Class.findById(id);

        if (entity == null) {
            throw new WebApplicationException("Class with id of " + id + " does not exist.", 404);
        }

        entity.className = entity_.className;
        entity.schedule = entity_.schedule;
        entity.teacher = entity_.teacher;

        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(Long id) {
        Class entity = Class.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Class with id of " + id + " does not exist.", 404);
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
