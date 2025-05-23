package com.richert.insurance.request.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PositiveOrZero
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(name = "InsuranceRequest", description = "User input for insurance premium calculation")
data class InsuranceRequestDTO(

    @NotBlank
    @field:Schema(description = "Postal code of the registration office", example = "10115")
    val postalCode: String,

    @NotBlank
    @field:Schema(
        description = "Type of the vehicle",
        example = "CAR",
        enumeration = ["CAR", "SUV", "TRUCK", "MOTORCYCLE"]
    )
    val vehicleType: String,

    @PositiveOrZero
    @field:Schema(description = "Estimated annual mileage", example = "12000")
    val annualMileage: Int
)
