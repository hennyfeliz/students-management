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
import org.acme.entities.Enrollment;
import org.acme.pagination.PagedResult;
import org.jboss.logging.Logger;

@Path("enrollment")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnrollmentResource {

    private static final Logger LOGGER = Logger.getLogger(EnrollmentResource.class.getName());


    @GET
    public Response listAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("sort") String sort
    ) {
        Long totalCount = Enrollment.count();
        size = (size == null) ? 10 : size;
        page = (page == null) ? 1 : page;

        return Response.ok(new PagedResult<>(Enrollment.findAll(Sort.by((sort == null) ? "id" : sort))
                .page(Page.of((page == null) ? 0 : page, (size == null) ? 10 : size))
                .list(),
                totalCount,
                (int) Math.ceil((double) totalCount / size),
                page > 0,
                (page + 1) * size < totalCount)).build();
    }

    @Path("{id}")
    public Enrollment getSingle(Long id) {
        Enrollment entity = Enrollment.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Enrollment with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    @POST
    @Transactional
    public Response create(Enrollment entity) {
        if (entity.id != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }

        entity.persist();
        return Response.ok(entity).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Enrollment update(Long id, Enrollment entity_) {
        //if (entity_.classs == null) {
        //    throw new WebApplicationException("Enrollment class was not set on request.", 422);
        //}

        Enrollment entity = Enrollment.findById(id);

        if (entity == null) {
            throw new WebApplicationException("Enrollment with id of " + id + " does not exist.", 404);
        }

        entity.enrollmentDate = entity_.enrollmentDate;
        entity.student = entity_.student;

        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(Long id) {
        Enrollment entity = Enrollment.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Enrollment with id of " + id + " does not exist.", 404);
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
