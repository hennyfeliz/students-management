package org.acme.controllers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.entities.School;
import org.acme.entities.Student;
import org.acme.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentResourceTest {

    @Inject
    SchoolResource schoolResource;

    @Inject
    StudentResource studentResource;

    @BeforeEach
    @Transactional
    public void setup() {
        // Limpiar la base de datos antes de cada prueba
        Student.deleteAll();
        School.deleteAll();
    }

    @Test
    @Transactional
    public void testListAllStudents() {
        // Crear una escuela para asociar a los estudiantes
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        // Insertar datos de prueba
        Student student1 = new Student();
        student1.studentName = "Student One";
        student1.school = school;
        given()
                .contentType("application/json")
                .body(student1)
                .when().post("/student")
                .then()
                .statusCode(201);

        Student student2 = new Student();
        student2.studentName = "Student Two";
        student2.school = school;
        given()
                .contentType("application/json")
                .body(student2)
                .when().post("/student")
                .then()
                .statusCode(201);

        given()
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when().get("/student")
                .then()
                .statusCode(200)
                .body("totalElements", is(2))
                .body("data.studentName", hasItems("Student One", "Student Two"));
    }

    @Test
    public void testCreateStudent() {
        // Crear una escuela para asociar al estudiante
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        Student newStudent = new Student();
        newStudent.studentName = "New Student";
        newStudent.gradeLevel = "Grade 1";
        newStudent.school = school;

        given()
                .contentType(ContentType.JSON)
                .body(newStudent)
                .when().post("/student")
                .then()
                .statusCode(201)
                .body("studentName", is("New Student"))
                .body("gradeLevel", is("Grade 1"))
                .body("school.id", is(school.id.intValue()));
    }

    @Test
    public void testUpdateStudent() {
        // Crear una escuela para asociar al estudiante
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        // Crear un estudiante para actualizar
        Student student = new Student();
        student.studentName = "Original Name";
        student.gradeLevel = "Grade 1";
        student.school = school;
        studentResource.create(student);

        // Crear los datos actualizados del estudiante
        Student updatedStudent = new Student();
        updatedStudent.studentName = "Updated Name";
        updatedStudent.gradeLevel = "Grade 2";
        updatedStudent.school = school;

        given()
                .contentType(ContentType.JSON)
                .body(updatedStudent)
                .when().put("/student/" + student.id)
                .then()
                .statusCode(200)
                .body("studentName", is("Updated Name"))
                .body("gradeLevel", is("Grade 2"))
                .body("school.id", is(school.id.intValue()));
    }

    @Test
    public void testDeleteStudent() {
        // Crear una escuela para asociar al estudiante
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        // Crear un estudiante para eliminar
        Student student = new Student();
        student.studentName = "Student to Delete";
        student.school = school;
        studentResource.create(student);

        // Fetch the created student to get the generated ID
        Student createdStudent = Student.findById(student.id);

        given()
                .when().delete("/student/" + createdStudent.id)
                .then()
                .statusCode(204);

        given()
                .when().get("/student/" + createdStudent.id)
                .then()
                .statusCode(404);
    }

    @Test
    public void testFindByStudentName() {
        // Crear una escuela para asociar al estudiante
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        Student student = new Student();
        student.studentName = "Find Me Student";
        student.school = school;
        studentResource.create(student);

        given()
                .queryParam("studentName", "Find Me Student")
                .when().get("/student")
                .then()
                .statusCode(200)
                .body("totalElements", is(1))
                .body("data[0].studentName", is("Find Me Student"));
    }

    @Test
    public void testFindByGradeLevel() {
        // Crear una escuela para asociar al estudiante
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        Student student = new Student();
        student.studentName = "Student";
        student.gradeLevel = "Specific Grade";
        student.school = school;
        studentResource.create(student);

        given()
                .queryParam("gradeLevel", "Specific Grade")
                .when().get("/student")
                .then()
                .statusCode(200)
                .body("totalElements", is(1))
                .body("data[0].gradeLevel", is("Specific Grade"));
    }

    @Test
    public void testFindBySchoolId() {
        // Crear una escuela para asociar al estudiante
        School school = new School();
        school.schoolName = "Test School";
        schoolResource.create(school);

        Student student = new Student();
        student.studentName = "Student";
        student.school = school;
        studentResource.create(student);

        given()
                .queryParam("schoolId", school.id)
                .when().get("/student")
                .then()
                .statusCode(200)
                .body("totalElements", is(1))
                .body("data[0].school.id", is(school.id.intValue()));
    }

}
