package com.scopevisio.insurance.request

import com.scopevisio.insurance.aspect.LoggedRequest
import com.scopevisio.insurance.request.model.InsuranceRequestDTO
import com.scopevisio.insurance.request.model.InsuranceResponseDTO
import com.scopevisio.insurance.utils.toResponseDTO
import com.scopevisio.insurance.vehicle.VehicleType
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.transaction.Transactional.TxType.SUPPORTS
import jakarta.validation.Valid
import jakarta.validation.constraints.PositiveOrZero
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import java.util.*

@LoggedRequest
@Path("/api/insurance-request")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Insurance Request", description = "Submit a request and receive a premium quote")
class InsuranceRequestResource {

    @Inject
    lateinit var insuranceRequestService: InsuranceRequestService

    @POST
    @Transactional
    @APIResponse(responseCode = "400", description = "Invalid input")
    @APIResponse(responseCode = "200", description = "Calculated premium returned successfully")
    @Operation(
        summary = "Calculate insurance premium",
        description = "Processes the input and returns calculated premium"
    )
    fun create(@Valid request: InsuranceRequestDTO): InsuranceResponseDTO {
        val entity = insuranceRequestService.processRequest(
            postalCode = request.postalCode,
            vehicleType = VehicleType.valueOf(request.vehicleType.uppercase()),
            annualMileage = request.annualMileage
        )

        return entity.toResponseDTO()
    }

    @GET
    @Path("/{id}")
    @Transactional(SUPPORTS)
    @Operation(summary = "Get insurance request by ID")
    fun getById(@PathParam("id") id: UUID): InsuranceResponseDTO {
        return insuranceRequestService.getById(id).toResponseDTO()
    }

    @GET
    @Valid
    @Transactional(SUPPORTS)
    @Operation(summary = "Get paged list of insurance requests")
    fun getAllPaged(
        @PositiveOrZero @QueryParam("page") page: Int = 0,
        @PositiveOrZero @QueryParam("size") size: Int? = 20
    ): List<InsuranceResponseDTO> {
        return insuranceRequestService
            .getAllPaged(page, size ?: 20)
            .map { it.toResponseDTO() }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @Operation(summary = "Delete insurance request by ID")
    fun delete(@PathParam("id") id: UUID): Boolean {
        return insuranceRequestService.deleteById(id)
    }
}
