package com.scopevisio.insurance.request

import com.scopevisio.insurance.PremiumCalculationService
import com.scopevisio.insurance.vehicle.VehicleType
import jakarta.enterprise.context.ApplicationScoped
import java.time.LocalDateTime

@ApplicationScoped
class InsuranceRequestService(
    private val premiumCalculationService: PremiumCalculationService
) {

    fun processRequest(
        postalCode: String,
        vehicleType: VehicleType,
        annualMileage: Int
    ): InsuranceRequest {
        val premium = premiumCalculationService.calculatePremium(postalCode, vehicleType, annualMileage)

        val entity = InsuranceRequest().apply {
            this.postalCode = postalCode
            this.vehicleType = vehicleType
            this.annualMileage = annualMileage
            this.calculatedPremium = premium
            this.createdAt = LocalDateTime.now()
        }

        entity.persist()
        return entity
    }
}
