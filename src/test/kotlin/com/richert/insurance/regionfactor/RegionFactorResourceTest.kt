package com.richert.insurance.regionfactor

import com.richert.insurance.PostgresTestResource
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestResource(PostgresTestResource::class)
class RegionFactorResourceTest {

    @Test
    fun getAllRegionFactorsTest() {
        given()
            .contentType(ContentType.JSON)
            .`when`()
            .get("/api/region-factors")
            .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("[0].state", notNullValue())
            .body("[0].factor", greaterThan(0.0f))
    }
}
