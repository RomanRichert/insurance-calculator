package com.richert.insurance.postcode

import com.richert.insurance.PostgresTestResource
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestResource(PostgresTestResource::class)
class PostCodeResourceTest {

    @Test
    fun getPostCodesTest() {
        given()
            .contentType(ContentType.JSON)
            .`when`()
            .get("/api/postcodes")
            .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("[0].postalCode", notNullValue())
            .body("[0].city", not(emptyOrNullString()))
    }

    @Test
    fun getFilteredPostCodesTest() {
        given()
            .queryParam("postalCode", "53721")
            .contentType(ContentType.JSON)
            .`when`()
            .get("/api/postcodes")
            .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(1))
            .body("[0].postalCode", equalTo("53721"))
    }
}
