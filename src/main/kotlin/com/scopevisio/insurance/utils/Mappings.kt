package com.scopevisio.insurance.utils

import com.scopevisio.insurance.postcode.model.PostCode
import com.scopevisio.insurance.postcode.model.PostCodeDTO
import com.scopevisio.insurance.regionfactor.model.RegionFactor
import com.scopevisio.insurance.regionfactor.model.RegionFactorDTO
import com.scopevisio.insurance.request.model.InsuranceListItemDTO
import com.scopevisio.insurance.request.model.InsuranceRequest
import com.scopevisio.insurance.request.model.InsuranceResponseDTO

fun RegionFactor.toDTO(): RegionFactorDTO = RegionFactorDTO(state = this.state, factor = this.factor)

fun InsuranceRequest.toListItemDTO(): InsuranceListItemDTO {
    return InsuranceListItemDTO(
        id = this.id ?: throw IllegalStateException("ID is null"),
        postalCode = this.postalCode,
        vehicleType = this.vehicleType.name,
        annualMileage = this.annualMileage,
        calculatedPremium = this.calculatedPremium,
        createdAt = this.createdAt
    )
}

fun InsuranceRequest.toResponseDTO(): InsuranceResponseDTO {
    return InsuranceResponseDTO(
        id = this.id ?: throw IllegalStateException("ID of the insurance request is null"),
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