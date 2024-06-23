package org.acme.controllers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.entities.Enrollment;
import org.acme.entities.Student;
import org.acme.entities.School;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Timestamp;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnrollmentResourceTest {

    @Inject
    EnrollmentResource enrollmentResource;

    @Inject
    StudentResource studentResource;

    @Inject
    SchoolResource schoolResource;

    @BeforeEach
    @Transactional
    public void setup() {
        // Limpiar la base de datos antes de cada prueba
        Enrollment.deleteAll();
        Student.deleteAll();
        School.deleteAll();
    }

    @Test
    @Transactional
    public void testListAllEnrollments() {
        // Crear una escuela y un estudiante para asociar a la inscripción
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        Student student = new Student();
        student.studentName = "Test Student";
        student.school = school;
        studentResource.create(student);

        // Insertar datos de prueba
        Enrollment enrollment1 = new Enrollment();
        enrollment1.student = student;
        enrollment1.enrollmentDate = new Timestamp(System.currentTimeMillis());
        given()
                .contentType("application/json")
                .body(enrollment1)
                .when().post("/enrollment")
                .then()
                .statusCode(201);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.student = student;
        enrollment2.enrollmentDate = new Timestamp(System.currentTimeMillis());
        given()
                .contentType("application/json")
                .body(enrollment2)
                .when().post("/enrollment")
                .then()
                .statusCode(201);

        given()
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when().get("/enrollment")
                .then()
                .statusCode(200)
                .body("totalElements", is(2));
    }

    @Test
    public void testCreateEnrollment() {
        // Crear una escuela y un estudiante para asociar a la inscripción
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        Student student = new Student();
        student.studentName = "Test Student";
        student.school = school;
        studentResource.create(student);

        Enrollment newEnrollment = new Enrollment();
        newEnrollment.student = student;
        newEnrollment.enrollmentDate = new Timestamp(System.currentTimeMillis());

        given()
                .contentType(ContentType.JSON)
                .body(newEnrollment)
                .when().post("/enrollment")
                .then()
                .statusCode(201)
                .body("student.id", is(student.id.intValue()))
                .body("enrollmentDate", is(newEnrollment.enrollmentDate.toString()));
    }

    @Test
    public void testUpdateEnrollment() {
        // Crear una escuela y un estudiante para asociar a la inscripción
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        Student student = new Student();
        student.studentName = "Test Student";
        student.school = school;
        studentResource.create(student);

        // Crear una inscripción para actualizar
        Enrollment enrollment = new Enrollment();
        enrollment.student = student;
        enrollment.enrollmentDate = new Timestamp(System.currentTimeMillis());
        enrollmentResource.create(enrollment);

        // Crear los datos actualizados de la inscripción
        Enrollment updatedEnrollment = new Enrollment();
        updatedEnrollment.student = student;
        updatedEnrollment.enrollmentDate = new Timestamp(System.currentTimeMillis() + 100000);

        given()
                .contentType(ContentType.JSON)
                .body(updatedEnrollment)
                .when().put("/enrollment/" + enrollment.id)
                .then()
                .statusCode(200)
                .body("student.id", is(student.id.intValue()))
                .body("enrollmentDate", is(updatedEnrollment.enrollmentDate.toString()));
    }

    @Test
    public void testDeleteEnrollment() {
        // Crear una escuela y un estudiante para asociar a la inscripción
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        Student student = new Student();
        student.studentName = "Test Student";
        student.school = school;
        studentResource.create(student);

        // Crear una inscripción para eliminar
        Enrollment enrollment = new Enrollment();
        enrollment.student = student;
        enrollment.enrollmentDate = new Timestamp(System.currentTimeMillis());
        enrollmentResource.create(enrollment);

        // Fetch the created enrollment to get the generated ID
        Enrollment createdEnrollment = Enrollment.findById(enrollment.id);

        given()
                .when().delete("/enrollment/" + createdEnrollment.id)
                .then()
                .statusCode(204);

        given()
                .when().get("/enrollment/" + createdEnrollment.id)
                .then()
                .statusCode(404);
    }

    @Test
    public void testFindByStudentId() {
        // Crear una escuela y un estudiante para asociar a la inscripción
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        Student student = new Student();
        student.studentName = "Test Student";
        student.school = school;
        studentResource.create(student);

        Enrollment enrollment = new Enrollment();
        enrollment.student = student;
        enrollment.enrollmentDate = new Timestamp(System.currentTimeMillis());
        enrollmentResource.create(enrollment);

        given()
                .queryParam("studentId", student.id)
                .when().get("/enrollment")
                .then()
                .statusCode(200)
                .body("totalElements", is(1))
                .body("data[0].student.id", is(student.id.intValue()));
    }

    @Test
    public void testFindByEnrollmentDate() {
        // Crear una escuela y un estudiante para asociar a la inscripción
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        Student student = new Student();
        student.studentName = "Test Student";
        student.school = school;
        studentResource.create(student);

        Timestamp enrollmentDate = new Timestamp(System.currentTimeMillis());

        Enrollment enrollment = new Enrollment();
        enrollment.student = student;
        enrollment.enrollmentDate = enrollmentDate;
        enrollmentResource.create(enrollment);

        given()
                .queryParam("enrollmentDate", enrollmentDate)
                .when().get("/enrollment")
                .then()
                .statusCode(200)
                .body("totalElements", is(1))
                .body("data[0].enrollmentDate", is(enrollmentDate.toString()));
    }
}
