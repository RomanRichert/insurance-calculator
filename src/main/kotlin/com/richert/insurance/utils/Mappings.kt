package com.richert.insurance.utils

import com.richert.insurance.postcode.model.PostCode
import com.richert.insurance.postcode.model.PostCodeDTO
import com.richert.insurance.regionfactor.model.RegionFactor
import com.richert.insurance.regionfactor.model.RegionFactorDTO
import com.richert.insurance.request.model.InsuranceRequest
import com.richert.insurance.request.model.InsuranceResponseDTO
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject

fun RegionFactor.toDTO(): RegionFactorDTO = RegionFactorDTO(state = this.state, factor = this.factor)

fun InsuranceRequest.toResponseDTO(): InsuranceResponseDTO {
    return InsuranceResponseDTO(
        id = this.id ?: throw IllegalStateException("ID of the insurance request is null"),
        postalCode = this.postalCode,
        vehicleType = this.vehicleType.name,
        annualMileage = this.annualMileage,
        calculatedPremium = this.calculatedPremium,
        createdAt = this.createdAt
    )
}

fun PostCode.toDTO(): PostCodeDTO {
    return PostCodeDTO(
        postalCode = this.postalCode,
        state = this.state,
        region = this.region,
        district = this.district,
        city = this.city,
        quarter = this.quarter
    )
}

fun JsonArray.toListOfJsonObjects(): List<JsonObject> {
    return this.map { it as JsonObject }
}