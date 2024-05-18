package org.acme.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
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
import org.acme.entities.Teacher;
import org.acme.pagination.PagedResult;
import org.jboss.logging.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.acme.Generics.GenericHelper.updateEntity;

@Path("teacher")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeacherResource {

    private static final Logger LOGGER = Logger.getLogger(TeacherResource.class.getName());


    @GET
    public Response listAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("sort") String sort,
            @QueryParam("teacherName") String teacherName,
            @QueryParam("subjectTaught") String subjectTaught,
            @QueryParam("schoolId") Long schoolId
    ) {
        size = (size == null) ? 10 : size;
        page = (page == null) ? 0 : page;
        sort = (Objects.equals(sort, "")) ? "teacherName" : sort;

        // Comenzamos con una consulta base
        String queryStr = "FROM Teacher WHERE 1=1";
        Map<String, Object> params = new HashMap<>();

        if (teacherName != null && !teacherName.isEmpty()) {
            queryStr += " AND LOWER(teacherName) LIKE LOWER(CONCAT('%', :teacherName, '%'))";
            params.put("teacherName", teacherName);
        }
        if (subjectTaught != null && !subjectTaught.isEmpty()) {
            queryStr += " AND LOWER(subjectTaught) LIKE LOWER(CONCAT('%', :subjectTaught, '%'))";
            params.put("subjectTaught", subjectTaught);
        }
        if (schoolId != null) {
            queryStr += " AND school.id = :schoolId";
            params.put("schoolId", schoolId);
        }

        // Crear la consulta con los parámetros acumulados
        PanacheQuery<Teacher> query = Teacher.find(queryStr, Sort.by(sort), params);

        Long totalCount = query.count();
        List<Teacher> teachers = query.page(Page.of(page, size)).list();

        return Response.ok(
                new PagedResult<>(
                        teachers,
                        totalCount,
                        (int) Math.ceil((double) totalCount / size),
                        page > 0,
                        (page + 1) * size < totalCount
                )
        ).build();
    }

    @Path("{id}")
    public Teacher getSingle(Long id) {
        Teacher entity = Teacher.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Teacher with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    @POST
    @Transactional
    public Response create(Teacher entity) {
        if (entity.id != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }

        entity.persist();
        return Response.ok(entity).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateTeacher(@PathParam("id") Long id, Teacher updatedTeacher) {
        Teacher existingTeacher = Teacher.findById(id);
        if (existingTeacher == null) {
            throw new WebApplicationException("Teacher with id " + id + " does not exist.", 404);
        }

        updateEntity(existingTeacher, updatedTeacher);

        return Response.ok(existingTeacher).build();
    }

//    @PUT
//    @Path("{id}")
//    @Transactional
//    public Teacher update(Long id, Teacher entity_) {
//        if (entity_.teacherName == null) {
//            throw new WebApplicationException("Teacher Name was not set on request.", 422);
//        }
//
//        Teacher entity = Teacher.findById(id);
//
//        if (entity == null) {
//            throw new WebApplicationException("Teacher with id of " + id + " does not exist.", 404);
//        }
//
//        entity.teacherName = entity_.teacherName;
//        entity.subjectTaught = entity_.subjectTaught;
//        entity.school = entity_.school;
//
//        return entity;
//    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(Long id) {
        Teacher entity = Teacher.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Teacher with id of " + id + " does not exist.", 404);
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
