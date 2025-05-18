package com.scopevisio.insurance.vehicle

import com.scopevisio.insurance.aspect.LoggedRequest
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@LoggedRequest
@Path("/api/vehicle-factors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Vehicle Factors", description = "Read-only access to vehicle-based factors")
class VehicleFactorResource {

    @Inject
    private lateinit var vehicleFactorService: VehicleFactorService

    @GET
    @Operation(summary = "List all configured vehicle factors")
    fun getAll(): List<VehicleFactorDTO> {
        return vehicleFactorService.getAllFactors().map { (type, factor) ->
            VehicleFactorDTO(vehicleType = type.name, factor = factor)
        }
    }
}