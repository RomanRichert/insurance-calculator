package com.scopevisio.insurance.request

import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/insurance-request")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Insurance Request", description = "Submit a request and receive a premium quote")
class InsuranceRequestResource {

    @Inject
    lateinit var insuranceRequestService: InsuranceRequestService

    @POST
    @APIResponse(responseCode = "400", description = "Invalid input")
    @APIResponse(responseCode = "200", description = "Calculated premium returned successfully")
    @Operation(
        summary = "Calculate insurance premium",
        description = "Processes the input and returns calculated premium"
    )
    fun create(request: InsuranceRequestDTO): InsuranceResponseDTO {
        val entity = insuranceRequestService.processRequest(
            postalCode = request.postalCode,
            vehicleType = request.vehicleType,
            annualMileage = request.annualMileage
        )

        return InsuranceResponseDTO(
            id = entity.id ?: throw IllegalStateException("Persisted entity has null ID"),
            calculatedPremium = entity.calculatedPremium,
            createdAt = entity.createdAt
        )
    }
}
