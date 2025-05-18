package com.scopevisio.insurance.request

import com.scopevisio.insurance.PremiumCalculationService
import com.scopevisio.insurance.request.model.InsuranceRequest
import com.scopevisio.insurance.vehicle.VehicleType
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.NotFoundException
import java.time.LocalDateTime
import java.util.*

@ApplicationScoped
class InsuranceRequestService(private val premiumCalculationService: PremiumCalculationService) {

    @Inject
    private lateinit var insuranceRequestRepository: InsuranceRequestRepository

    fun processRequest(postalCode: String, vehicleType: VehicleType, annualMileage: Int): InsuranceRequest {
        val premium = premiumCalculationService.calculatePremium(postalCode, vehicleType, annualMileage)

        val entity = InsuranceRequest().apply {
            this.postalCode = postalCode
            this.vehicleType = vehicleType
            this.annualMileage = annualMileage
            this.calculatedPremium = premium
            this.createdAt = LocalDateTime.now()
        }

        insuranceRequestRepository.persist(entity)
        return entity
    }

    fun getById(id: UUID): InsuranceRequest {
        return insuranceRequestRepository.findById(id) ?: throw NotFoundException("Insurance request not found: $id")
    }

    fun getAllPaged(page: Int, size: Int): List<InsuranceRequest> {
        return insuranceRequestRepository.findPaged(page, size)
    }

    fun deleteById(id: UUID): Boolean {
        val deleted = insuranceRequestRepository.deleteById(id)
        return if (deleted) true else throw NotFoundException("Insurance request not found: $id")
    }
}