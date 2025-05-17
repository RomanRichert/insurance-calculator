package com.scopevisio.insurance.request.model

import org.eclipse.microprofile.openapi.annotations.media.Schema
import java.time.LocalDateTime
import java.util.*

@Schema(name = "InsuranceListItem", description = "Short summary of an insurance request")
data class InsuranceListItemDTO(

    @field:Schema(description = "Unique ID of the request")
    val id: UUID,

    @field:Schema(description = "Postal code", example = "10115")
    val postalCode: String,

    @field:Schema(description = "Vehicle type", example = "SUV")
    val vehicleType: String,

    @field:Schema(description = "Annual mileage", example = "15000")
    val annualMileage: Int,

    @field:Schema(description = "Calculated premium", example = "1.8")
    val calculatedPremium: Double,

    @field:Schema(description = "Timestamp of request creation")
    val createdAt: LocalDateTime
)