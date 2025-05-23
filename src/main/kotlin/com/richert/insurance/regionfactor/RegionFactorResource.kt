package com.richert.insurance.regionfactor

import com.richert.insurance.aspect.LoggedRequest
import com.richert.insurance.regionfactor.model.RegionFactorDTO
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.transaction.Transactional.TxType.SUPPORTS
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@LoggedRequest
@Path("/api/region-factors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Region Factors", description = "Read-only access to region-based insurance factors")
class RegionFactorResource {

    @Inject
    private lateinit var regionFactorService: RegionFactorService

    @GET
    @Transactional(SUPPORTS)
    @Operation(summary = "List all region factors")
    fun getAll(): List<RegionFactorDTO> {
        return regionFactorService.getAll()
    }
}