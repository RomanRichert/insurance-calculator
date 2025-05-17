package com.scopevisio.insurance.request.model

import org.eclipse.microprofile.openapi.annotations.media.Schema
import java.time.LocalDateTime
import java.util.*

@Schema(name = "InsuranceResponse", description = "Calculated insurance premium and metadata")
data class InsuranceResponseDTO(

    @field:Schema(description = "Unique ID of the request")
    val id: UUID,

    @field:Schema(description = "Calculated insurance premium", example = "1.8")
    val calculatedPremium: Double,

    @field:Schema(description = "Timestamp of creation")
    val createdAt: LocalDateTime
)
