package com.scopevisio.insurance.vehicle

import io.smallrye.config.ConfigMapping

@ConfigMapping(prefix = "vehicle")
interface VehicleFactorConfig {
    fun factors(): Map<String, Double>
}
