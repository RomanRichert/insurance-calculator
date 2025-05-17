package com.scopevisio.insurance.request

import com.scopevisio.insurance.aspect.LoggedRequest
import com.scopevisio.insurance.request.model.InsuranceListItemDTO
import com.scopevisio.insurance.request.model.InsuranceRequestDTO
import com.scopevisio.insurance.request.model.InsuranceResponseDTO
import com.scopevisio.insurance.utils.toListItemDTO
import com.scopevisio.insurance.utils.toResponseDTO
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.transaction.Transactional.TxType.SUPPORTS
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
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
    fun create(request: InsuranceRequestDTO): InsuranceResponseDTO {
        val entity = insuranceRequestService.processRequest(
            postalCode = request.postalCode,
            vehicleType = request.vehicleType,
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
    @Transactional(SUPPORTS)
    @Operation(summary = "Get paged list of insurance requests")
    fun getAllPaged(
        @QueryParam("page") page: Int = 0,
        @QueryParam("size") size: Int? = 20
    ): List<InsuranceListItemDTO> {
        return insuranceRequestService
            .getAllPaged(page, size ?: 20)
            .map { it.toListItemDTO() }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @Operation(summary = "Delete insurance request by ID")
    fun delete(@PathParam("id") id: UUID): Response {
        insuranceRequestService.deleteById(id)
        return Response.noContent().build()
    }
}
