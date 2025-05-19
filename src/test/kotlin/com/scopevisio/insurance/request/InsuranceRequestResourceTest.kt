package com.scopevisio.insurance.request

import com.scopevisio.insurance.PostgresTestResource
import com.scopevisio.insurance.request.model.InsuranceRequestDTO
import com.scopevisio.insurance.vehicle.VehicleType
import io.quarkus.test.TestTransaction
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import java.util.*

@QuarkusTest
@Suppress("RedundantModalityModifier")
@QuarkusTestResource(PostgresTestResource::class)
open class InsuranceRequestResourceTest {

    @Test
    fun createInsuranceRequestTest() {
        val request = InsuranceRequestDTO(
            postalCode = "53721",
            vehicleType = VehicleType.CAR.name,
            annualMileage = 15000
        )

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .`when`()
            .post("/api/insurance-request")
            .then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("calculatedPremium", greaterThan(0.0F))
    }

    @Test
    fun getInsuranceRequestByIdTest() {
        val id = createTestInsuranceRequest()

        given()
            .accept(ContentType.JSON)
            .`when`()
            .get("/api/insurance-request/$id")
            .then()
            .statusCode(200)
            .body("id", equalTo(id.toString()))
            .body("calculatedPremium", greaterThan(0.0F))
    }

    @Test
    fun getAllInsuranceRequestsPagedTest() {
        createTestInsuranceRequest()

        given()
            .accept(ContentType.JSON)
            .queryParam("page", 0)
            .queryParam("size", 10)
            .`when`()
            .get("/api/insurance-request")
            .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
    }

    @Test
    fun deleteInsuranceRequestTest() {
        val id = createTestInsuranceRequest()

        given()
            .accept(ContentType.JSON)
            .`when`()
            .delete("/api/insurance-request/$id")
            .then()
            .statusCode(200)

        given()
            .accept(ContentType.JSON)
            .`when`()
            .get("/api/insurance-request/$id")
            .then()
            .statusCode(404)
    }

    @TestTransaction
    protected fun createTestInsuranceRequest(): UUID {
        val request = InsuranceRequestDTO(
            postalCode = "53721",
            vehicleType = VehicleType.CAR.name,
            annualMileage = 30000
        )

        val response = given()
            .contentType(ContentType.JSON)
            .body(request)
            .`when`()
            .post("/api/insurance-request")
            .then()
            .statusCode(200)
            .extract()
            .path<String>("id")

        return UUID.fromString(response)
    }
}
