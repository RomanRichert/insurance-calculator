package com.scopevisio.insurance.vehicle

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class VehicleFactorService {

    @Inject
    private lateinit var config: VehicleFactorConfig

    private val defaultFactors = mapOf(
        VehicleType.CAR to 1.0,
        VehicleType.SUV to 1.2,
        VehicleType.TRUCK to 1.5,
        VehicleType.MOTORCYCLE to 0.8
    )

    fun getFactor(type: VehicleType): Double {
        val custom = config.factors()[type.name]
        return custom ?: defaultFactors[type]
        ?: throw IllegalArgumentException("No factor configured for vehicle type: ${type.name}")
    }

    fun getAllFactors(): Map<VehicleType, Double> {
        return VehicleType.entries.associateWith { getFactor(it) }
    }
}
