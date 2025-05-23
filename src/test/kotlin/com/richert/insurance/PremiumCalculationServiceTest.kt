package com.richert.insurance

import com.richert.insurance.vehicle.VehicleType
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@QuarkusTest
class PremiumCalculationServiceTest {

    @Inject
    private lateinit var premiumCalculationService: PremiumCalculationService

    @Test
    fun calculatePremiumTest() {
        val postalCode = "53721"
        val vehicleType = VehicleType.CAR
        val annualMileage = 30000

        val result = premiumCalculationService.calculatePremium(postalCode, vehicleType, annualMileage)

        // 2.0 (mileage) * 1.0 (vehicle) * 1.1 (region) = 2.2
        assertEquals(2.2, result)
    }
}
