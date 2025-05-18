package com.scopevisio.insurance.vehicle

import io.smallrye.config.ConfigMapping

/**
 * Maps configuration properties under the `vehicle` prefix into a typed structure.
 *
 * Expected format in application.yml:
 * ```
 * vehicle:
 *   factors:
 *     CAR: 1.0
 *     SUV: 1.2
 *     TRUCK: 1.5
 * ```
 *
 * This configuration is used to define premium calculation factors by vehicle type.
 */
@ConfigMapping(prefix = "vehicle")
interface VehicleFactorConfig {
    fun factors(): Map<String, Double>
}
