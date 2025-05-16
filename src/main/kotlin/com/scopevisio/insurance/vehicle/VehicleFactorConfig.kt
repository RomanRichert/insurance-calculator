package com.scopevisio.insurance.vehicle

import io.smallrye.config.ConfigMapping

@ConfigMapping(prefix = "vehicle.factors")
interface VehicleFactorConfig {
    fun get(): Map<String, Double>
}
