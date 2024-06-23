package org.acme.controllers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.entities.Teacher;
import org.acme.entities.School;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TeacherResourceTest {

    @Inject
    TeacherResource teacherResource;

    @Inject
    SchoolResource schoolResource;

    @BeforeEach
    @Transactional
    public void setup() {
        // Limpiar la base de datos antes de cada prueba
        Teacher.deleteAll();
        School.deleteAll();
    }

    @Test
    @Transactional
    public void testListAllTeachers() {
        // Crear una escuela para asociar a los profesores
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        // Insertar datos de prueba
        Teacher teacher1 = new Teacher();
        teacher1.teacherName = "Teacher One";
        teacher1.school = school;
        given()
                .contentType("application/json")
                .body(teacher1)
                .when().post("/teacher")
                .then()
                .statusCode(201);

        Teacher teacher2 = new Teacher();
        teacher2.teacherName = "Teacher Two";
        teacher2.school = school;
        given()
                .contentType("application/json")
                .body(teacher2)
                .when().post("/teacher")
                .then()
                .statusCode(201);

        given()
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when().get("/teacher")
                .then()
                .statusCode(200)
                .body("totalElements", is(2))
                .body("data.teacherName", hasItems("Teacher One", "Teacher Two"));
    }

    @Test
    public void testCreateTeacher() {
        // Crear una escuela para asociar al profesor
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        Teacher newTeacher = new Teacher();
        newTeacher.teacherName = "New Teacher";
        newTeacher.subjectTaught = "Mathematics";
        newTeacher.school = school;

        given()
                .contentType(ContentType.JSON)
                .body(newTeacher)
                .when().post("/teacher")
                .then()
                .statusCode(201)
                .body("teacherName", is("New Teacher"))
                .body("subjectTaught", is("Mathematics"))
                .body("school.id", is(school.id.intValue()));
    }

    @Test
    public void testUpdateTeacher() {
        // Crear una escuela para asociar al profesor
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        // Crear un profesor para actualizar
        Teacher teacher = new Teacher();
        teacher.teacherName = "Original Name";
        teacher.subjectTaught = "History";
        teacher.school = school;
        teacherResource.create(teacher);

        // Crear los datos actualizados del profesor
        Teacher updatedTeacher = new Teacher();
        updatedTeacher.teacherName = "Updated Name";
        updatedTeacher.subjectTaught = "Geography";
        updatedTeacher.school = school;

        given()
                .contentType(ContentType.JSON)
                .body(updatedTeacher)
                .when().put("/teacher/" + teacher.id)
                .then()
                .statusCode(200)
                .body("teacherName", is("Updated Name"))
                .body("subjectTaught", is("Geography"))
                .body("school.id", is(school.id.intValue()));
    }

    @Test
    public void testDeleteTeacher() {
        // Crear una escuela para asociar al profesor
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        // Crear un profesor para eliminar
        Teacher teacher = new Teacher();
        teacher.teacherName = "Teacher to Delete";
        teacher.school = school;
        teacherResource.create(teacher);

        // Fetch the created teacher to get the generated ID
        Teacher createdTeacher = Teacher.findById(teacher.id);

        given()
                .when().delete("/teacher/" + createdTeacher.id)
                .then()
                .statusCode(204);

        given()
                .when().get("/teacher/" + createdTeacher.id)
                .then()
                .statusCode(404);
    }

    @Test
    public void testFindByTeacherName() {
        // Crear una escuela para asociar al profesor
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        Teacher teacher = new Teacher();
        teacher.teacherName = "Find Me Teacher";
        teacher.school = school;
        teacherResource.create(teacher);

        given()
                .queryParam("teacherName", "Find Me Teacher")
                .when().get("/teacher")
                .then()
                .statusCode(200)
                .body("totalElements", is(1))
                .body("data[0].teacherName", is("Find Me Teacher"));
    }

    @Test
    public void testFindBySubjectTaught() {
        // Crear una escuela para asociar al profesor
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        Teacher teacher = new Teacher();
        teacher.teacherName = "Teacher";
        teacher.subjectTaught = "Science";
        teacher.school = school;
        teacherResource.create(teacher);

        given()
                .queryParam("subjectTaught", "Science")
                .when().get("/teacher")
                .then()
                .statusCode(200)
                .body("totalElements", is(1))
                .body("data[0].subjectTaught", is("Science"));
    }

    @Test
    public void testFindBySchoolId() {
        // Crear una escuela para asociar al profesor
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        Teacher teacher = new Teacher();
        teacher.teacherName = "Teacher";
        teacher.school = school;
        teacherResource.create(teacher);

        given()
                .queryParam("schoolId", school.id)
                .when().get("/teacher")
                .then()
                .statusCode(200)
                .body("totalElements", is(1))
                .body("data[0].school.id", is(school.id.intValue()));
    }
}
