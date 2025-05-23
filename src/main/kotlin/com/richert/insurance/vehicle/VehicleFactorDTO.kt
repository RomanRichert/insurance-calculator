package com.richert.insurance.vehicle

import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(name = "VehicleFactor", description = "Insurance factor by vehicle type")
data class VehicleFactorDTO(

    @field:Schema(description = "Vehicle type", example = "SUV")
    val vehicleType: String,

    @field:Schema(description = "Associated factor", example = "1.2")
    val factor: Double
)