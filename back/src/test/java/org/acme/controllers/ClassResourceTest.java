package org.acme.controllers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.entities.Class;
import org.acme.entities.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClassResourceTest {

    @Inject
    TeacherResource teacherResource;

    @Inject
    ClassResource classResource;

    @BeforeEach
    @Transactional
    public void setup() {
        // Limpiar la base de datos antes de cada prueba
        Class.deleteAll();
        Teacher.deleteAll();
    }

    @Test
    @Transactional
    public void testListAllClasses() {
        // Crear un profesor para asociar a las clases
        Teacher teacher = new Teacher();
        teacher.teacherName = "Test Teacher";
        teacherResource.create(teacher);

        // Insertar datos de prueba
        Class class1 = new Class();
        class1.className = "Class One";
        class1.teacher = teacher;
        given()
                .contentType("application/json")
                .body(class1)
                .when().post("/class")
                .then()
                .statusCode(201);

        Class class2 = new Class();
        class2.className = "Class Two";
        class2.teacher = teacher;
        given()
                .contentType("application/json")
                .body(class2)
                .when().post("/class")
                .then()
                .statusCode(201);

        given()
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when().get("/class")
                .then()
                .statusCode(200)
                .body("totalElements", is(2))
                .body("data.className", hasItems("Class One", "Class Two"));
    }

    @Test
    public void testCreateClass() {
        // Crear un profesor para asociar a la clase
        Teacher teacher = new Teacher();
        teacher.teacherName = "Test Teacher";
        teacherResource.create(teacher);

        Class newClass = new Class();
        newClass.className = "New Class";
        newClass.schedule = "Mon-Fri 10-11AM";
        newClass.teacher = teacher;

        given()
                .contentType(ContentType.JSON)
                .body(newClass)
                .when().post("/class")
                .then()
                .statusCode(201)
                .body("className", is("New Class"))
                .body("schedule", is("Mon-Fri 10-11AM"))
                .body("teacher.id", is(teacher.id.intValue()));
    }

    @Test
    public void testUpdateClass() {
        // Crear un profesor para asociar a la clase
        Teacher teacher = new Teacher();
        teacher.teacherName = "Test Teacher";
        teacherResource.create(teacher);

        // Crear una clase para actualizar
        Class classEntity = new Class();
        classEntity.className = "Original Name";
        classEntity.schedule = "Mon-Fri 9-10AM";
        classEntity.teacher = teacher;
        classResource.create(classEntity);

        // Crear los datos actualizados de la clase
        Class updatedClass = new Class();
        updatedClass.className = "Updated Name";
        updatedClass.schedule = "Mon-Fri 10-11AM";
        updatedClass.teacher = teacher;

        given()
                .contentType(ContentType.JSON)
                .body(updatedClass)
                .when().put("/class/" + classEntity.id)
                .then()
                .statusCode(200)
                .body("className", is("Updated Name"))
                .body("schedule", is("Mon-Fri 10-11AM"))
                .body("teacher.id", is(teacher.id.intValue()));
    }

    @Test
    public void testDeleteClass() {
        // Crear un profesor para asociar a la clase
        Teacher teacher = new Teacher();
        teacher.teacherName = "Test Teacher";
        teacherResource.create(teacher);

        // Crear una clase para eliminar
        Class classEntity = new Class();
        classEntity.className = "Class to Delete";
        classEntity.teacher = teacher;
        classResource.create(classEntity);

        // Fetch the created class to get the generated ID
        Class createdClass = Class.findById(classEntity.id);

        given()
                .when().delete("/class/" + createdClass.id)
                .then()
                .statusCode(204);

        given()
                .when().get("/class/" + createdClass.id)
                .then()
                .statusCode(404);
    }

    @Test
    public void testFindByClassName() {
        // Crear un profesor para asociar a la clase
        Teacher teacher = new Teacher();
        teacher.teacherName = "Test Teacher";
        teacherResource.create(teacher);

        Class classEntity = new Class();
        classEntity.className = "Find Me Class";
        classEntity.teacher = teacher;
        classResource.create(classEntity);

        given()
                .queryParam("className", "Find Me Class")
                .when().get("/class")
                .then()
                .statusCode(200)
                .body("totalElements", is(1))
                .body("data[0].className", is("Find Me Class"));
    }

    @Test
    public void testFindBySchedule() {
        // Crear un profesor para asociar a la clase
        Teacher teacher = new Teacher();
        teacher.teacherName = "Test Teacher";
        teacherResource.create(teacher);

        Class classEntity = new Class();
        classEntity.className = "Class";
        classEntity.schedule = "Specific Schedule";
        classEntity.teacher = teacher;
        classResource.create(classEntity);

        given()
                .queryParam("schedule", "Specific Schedule")
                .when().get("/class")
                .then()
                .statusCode(200)
                .body("totalElements", is(1))
                .body("data[0].schedule", is("Specific Schedule"));
    }

    @Test
    public void testFindByTeacherId() {
        // Crear un profesor para asociar a la clase
        Teacher teacher = new Teacher();
        teacher.teacherName = "Test Teacher";
        teacherResource.create(teacher);

        Class classEntity = new Class();
        classEntity.className = "Class";
        classEntity.teacher = teacher;
        classResource.create(classEntity);

        given()
                .queryParam("teacherId", teacher.id)
                .when().get("/class")
                .then()
                .statusCode(200)
                .body("totalElements", is(1))
                .body("data[0].teacher.id", is(teacher.id.intValue()));
    }
}
