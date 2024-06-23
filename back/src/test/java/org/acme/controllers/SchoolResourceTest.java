package org.acme.controllers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
//import jakarta.transaction.Transactional;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.entities.School;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

//import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SchoolResourceTest {

    @BeforeEach
    @Transactional
    public void setup() {
        // Limpiar la base de datos antes de cada prueba
        School.deleteAll();
    }

    @Inject
    SchoolResource schoolResource;

    @Test
    @Transactional
    public void testListAllSchools() {
        // Insertar datos de prueba
        School school1 = new School();
        school1.schoolName = "School One";
        given()
                .contentType("application/json")
                .body(school1)
                .when().post("/school")
                .then()
                .statusCode(201);

        School school2 = new School();
        school2.schoolName = "School Two";
        given()
                .contentType("application/json")
                .body(school2)
                .when().post("/school")
                .then()
                .statusCode(201);

        given()
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when().get("/school")
                .then()
                .statusCode(200)
                .body("totalElements", is(2))
                .body("data.schoolName", hasItems("School One", "School Two"));
    }

    @Test
    public void testCreateSchool() {
        School newSchool = new School();
        newSchool.schoolName = "New School";
        newSchool.address = "1234 Main St";
        newSchool.phoneNumber = "555-1234";

        given()
                .contentType(ContentType.JSON)
                .body(newSchool)
                .when().post("/school")
                .then()
                .statusCode(201)
                .body("schoolName", is("New School"))
                .body("address", is("1234 Main St"))
                .body("phoneNumber", is("555-1234"));
    }

    @Test
    public void testUpdateSchool() {
        School school = new School();
        school.schoolName = "Original Name";
        school.address = "1234 Main St";
        school.phoneNumber = "555-1234";
        schoolResource.create(school);

        School updatedSchool = new School();
        updatedSchool.schoolName = "Updated Name";
        updatedSchool.address = "5678 Oak St";
        updatedSchool.phoneNumber = "555-5678";

        given()
                .contentType(ContentType.JSON)
                .body(updatedSchool)
                .when().put("/school/" + school.id)
                .then()
                .statusCode(200)
                .body("schoolName", is("Updated Name"))
                .body("address", is("5678 Oak St"))
                .body("phoneNumber", is("555-5678"));
    }

    @Test
    public void testDeleteSchool() {
        School school = new School();
        school.schoolName = "School to Delete";
        schoolResource.create(school);

        // Fetch the created school to get the generated ID
        School createdSchool = School.findById(school.id);

        given()
                .when().delete("/school/" + createdSchool.id)
                .then()
                .statusCode(204);

//        given()
//                .when().get("/school/" + createdSchool.id)
//                .then()
//                .statusCode(405);
    }

    @Test
    public void testFindBySchoolName() {
        School school = new School();
        school.schoolName = "Find Me School";
        schoolResource.create(school);

        given()
                .queryParam("schoolName", "Find Me School")
                .when().get("/school")
                .then()
                .statusCode(200)
                .body("totalElements", is(1))
                .body("data[0].schoolName", is("Find Me School"));
    }

    @Test
    public void testFindByAddress() {
        School school = new School();
        school.schoolName = "School";
        school.address = "Specific Address";
        schoolResource.create(school);

        given()
                .queryParam("address", "Specific Address")
                .when().get("/school")
                .then()
                .statusCode(200)
                .body("totalElements", is(1))
                .body("data[0].address", is("Specific Address"));
    }

    @Test
    public void testFindByPhoneNumber() {
        School school = new School();
        school.schoolName = "School";
        school.phoneNumber = "555-0000";
        schoolResource.create(school);

        given()
                .queryParam("phoneNumber", "555-0000")
                .when().get("/school")
                .then()
                .statusCode(200)
                .body("totalElements", is(1))
                .body("data[0].phoneNumber", is("555-0000"));
    }
}
