package com.scopevisio.insurance.postcode.model

import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(name = "PostCode", description = "Imported postcode with geographic regions")
data class PostCodeDTO(

    @field:Schema(description = "Postleitzahl", example = "10115")
    val postalCode: String,

    @field:Schema(description = "REGION1", example = "Berlin")
    val state: String,

    @field:Schema(description = "REGION2", example = "Berlin")
    val region: String?,

    @field:Schema(description = "REGION3", example = "Berlin")
    val district: String?,

    @field:Schema(description = "REGION4", example = "Berlin")
    val city: String?,

    @field:Schema(description = "AREA1", example = "Mitte")
    val quarter: String?
)