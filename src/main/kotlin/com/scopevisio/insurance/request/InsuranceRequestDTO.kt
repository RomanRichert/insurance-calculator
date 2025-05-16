package com.scopevisio.insurance.request

import com.scopevisio.insurance.vehicle.VehicleType
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(name = "InsuranceRequest", description = "User input for insurance premium calculation")
data class InsuranceRequestDTO(

    @field:Schema(description = "Postal code of the registration office", example = "10115")
    val postalCode: String,

    @field:Schema(description = "Type of the vehicle", example = "CAR")
    val vehicleType: VehicleType,

    @field:Schema(description = "Estimated annual mileage", example = "12000")
    val annualMileage: Int
)
