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
import org.acme.entities.Student;
import org.acme.pagination.PagedResult;
import org.jboss.logging.Logger;

@Path("student")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {
    private static final Logger LOGGER = Logger.getLogger(StudentResource.class.getName());


    @GET
    public Response listAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("sort") String sort
    ) {
        Long totalCount = Student.count();
        size = (size == null) ? 10 : size;
        page = (page == null) ? 1 : page;

        return Response.ok(new PagedResult<>(Student.findAll(Sort.by((sort == null) ? "studentName" : sort))
                .page(Page.of((page == null) ? 0 : page, (size == null) ? 10 : size))
                .list(),
                totalCount,
                (int) Math.ceil((double) totalCount / size),
                page > 0,
                (page + 1) * size < totalCount)).build();
    }

    @Path("{id}")
    public Student getSingle(Long id) {
        Student entity = Student.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Student with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    @POST
    @Transactional
    public Response create(Student entity) {
        if (entity.id != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }

        entity.persist();
        return Response.ok(entity).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Student update(Long id, Student entity_) {
        if (entity_.studentName == null) {
            throw new WebApplicationException("Student Name was not set on request.", 422);
        }

        Student entity = Student.findById(id);

        if (entity == null) {
            throw new WebApplicationException("Student with id of " + id + " does not exist.", 404);
        }

        entity.studentName = entity_.studentName;
        entity.gradeLevel = entity_.gradeLevel;
        entity.school = entity_.school;

        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(Long id) {
        Student entity = Student.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Student with id of " + id + " does not exist.", 404);
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
