package com.scopevisio.insurance.vehicle

import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@QuarkusTest
class VehicleFactorServiceTest {

    @Inject
    private lateinit var vehicleFactorService: VehicleFactorService

    @Test
    fun getFactorDefaultTest() {

        assertEquals(1.0, vehicleFactorService.getFactor(VehicleType.CAR))
        assertEquals(1.2, vehicleFactorService.getFactor(VehicleType.SUV))
        assertEquals(1.5, vehicleFactorService.getFactor(VehicleType.TRUCK))
        assertEquals(0.8, vehicleFactorService.getFactor(VehicleType.MOTORCYCLE))
    }

    @Test
    fun getAllFactorsTest() {

        val allFactors = vehicleFactorService.getAllFactors()

        assertEquals(1.0, allFactors[VehicleType.CAR])
        assertEquals(1.2, allFactors[VehicleType.SUV])
        assertEquals(1.5, allFactors[VehicleType.TRUCK])
        assertEquals(0.8, allFactors[VehicleType.MOTORCYCLE])
    }
}
