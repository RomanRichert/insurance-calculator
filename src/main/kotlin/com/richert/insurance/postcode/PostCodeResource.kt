package com.richert.insurance.postcode

import com.richert.insurance.aspect.LoggedRequest
import com.richert.insurance.postcode.model.PostCodeDTO
import com.richert.insurance.utils.toDTO
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.transaction.Transactional.TxType.SUPPORTS
import jakarta.validation.Valid
import jakarta.validation.constraints.PositiveOrZero
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@LoggedRequest
@Path("/api/postcodes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Postcodes", description = "Access to imported postcode registry")
class PostCodeResource {

    @Inject
    private lateinit var postCodeService: PostCodeService

    @GET
    @Valid
    @Transactional(SUPPORTS)
    @Operation(summary = "Get postcodes with optional filters and pagination")
    fun getPostCodes(
        @QueryParam("postalCode") postalCode: String?,
        @QueryParam("state") state: String?,
        @QueryParam("region") region: String?,
        @QueryParam("district") district: String?,
        @QueryParam("city") city: String?,
        @QueryParam("quarter") quarter: String?,
        @PositiveOrZero @QueryParam("page") page: Int = 0,
        @PositiveOrZero @QueryParam("size") size: Int? = 20
    ): List<PostCodeDTO> {

        return postCodeService
            .getFiltered(postalCode, state, region, district, city, quarter, page, size ?: 20)
            .map { it.toDTO() }
    }
}