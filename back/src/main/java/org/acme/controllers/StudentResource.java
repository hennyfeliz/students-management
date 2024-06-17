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
import org.acme.entities.Student;
import org.acme.pagination.PagedResult;
import org.jboss.logging.Logger;

import java.util.*;

import static org.acme.Generics.GenericHelper.updateEntity;

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
            @QueryParam("sort") String sort,
            @QueryParam("studentName") String studentName,
            @QueryParam("gradeLevel") String gradeLevel,
            @QueryParam("schoolId") Long schoolId
    ) {
        size = (size == null) ? 10 : size;
        page = (page == null) ? 0 : page;
        sort = (Objects.equals(sort, "")) ? "studentName" : sort;

        String validSortColumn = "studentName";

        List<String> validSortColumns = Arrays.asList("studentName", "gradeLevel", "schoolId");
        if (validSortColumns.contains(sort)) {
            validSortColumn = sort;
        }

        // Comenzamos con una consulta base
        String queryStr = "FROM Student WHERE 1=1";
        Map<String, Object> params = new HashMap<>();

        if (studentName != null && !studentName.isEmpty()) {
            queryStr += " AND LOWER(studentName) LIKE LOWER(CONCAT('%', :studentName, '%'))";
            params.put("studentName", studentName);
        }
        if (gradeLevel != null && !gradeLevel.isEmpty()) {
            queryStr += " AND LOWER(gradeLevel) LIKE LOWER(CONCAT('%', :gradeLevel, '%'))";
            params.put("gradeLevel", gradeLevel);
        }
        if (schoolId != null) {
            queryStr += " AND school.id = :schoolId";
            params.put("schoolId", schoolId);
        }

        // Crear la consulta con los parámetros acumulados
        PanacheQuery<Student> query = Student.find(queryStr, Sort.by(validSortColumn), params);

        Long totalCount = query.count();
        List<Student> students = query.page(Page.of(page, size)).list();

        return Response.ok(
                new PagedResult<>(
                        students,
                        totalCount,
                        (int) Math.ceil((double) totalCount / size),
                        page > 0,
                        (page + 1) * size < totalCount
                )
        ).build();
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
    public Response updateStudent(@PathParam("id") Long id, Student updatedStudent) {
        Student existingStudent = Student.findById(id);
        if (existingStudent == null) {
            throw new WebApplicationException("Student with id " + id + " does not exist.", 404);
        }

        updateEntity(existingStudent, updatedStudent);

        return Response.ok(existingStudent).build();
    }


//    @PUT
//    @Path("{id}")
//    @Transactional
//    public Student update(Long id, Student entity_) {
//        if (entity_.studentName == null) {
//            throw new WebApplicationException("Student Name was not set on request.", 422);
//        }
//
//        Student entity = Student.findById(id);
//
//        if (entity == null) {
//            throw new WebApplicationException("Student with id of " + id + " does not exist.", 404);
//        }
//
//        entity.studentName = entity_.studentName;
//        entity.gradeLevel = entity_.gradeLevel;
//        entity.school = entity_.school;
//
//        return entity;
//    }

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
