package com.richert.insurance

import com.richert.insurance.postcode.PostCodeRepository
import com.richert.insurance.regionfactor.RegionFactorRepository
import com.richert.insurance.vehicle.VehicleFactorService
import com.richert.insurance.vehicle.VehicleType
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.jboss.logging.Logger

/**
 * Service responsible for calculating the insurance premium based on:
 * - annual mileage
 * - vehicle type
 * - regional factor derived from the postal code
 *
 * The premium is calculated using the following formula:
 *
 * `mileageFactor * vehicleTypeFactor * regionFactor`
 */
@ApplicationScoped
class PremiumCalculationService(
    private val vehicleFactorService: VehicleFactorService,
    private val postCodeRepository: PostCodeRepository,
    private val regionFactorRepository: RegionFactorRepository
) {

    private val logger: Logger = Logger.getLogger(javaClass)

    @Transactional
    fun calculatePremium(postalCode: String, vehicleType: VehicleType, annualMileage: Int): Double {
        val mileageFactor = mileageFactor(annualMileage)

        val postCode = postCodeRepository.find("postalCode", postalCode).firstResult()
            ?: throw IllegalArgumentException("Unknown postal code: $postalCode")

        val regionFactor = regionFactorRepository.find("state", postCode.state).firstResult()
            ?: throw IllegalArgumentException("No region factor for state: ${postCode.state}")

        val vehicleFactor = vehicleFactorService.getFactor(vehicleType)

        val premium = mileageFactor * vehicleFactor * regionFactor.factor

        logger.debug("Calculated premium = $mileageFactor * $vehicleFactor * ${regionFactor.factor} = $premium")

        return premium
    }

    private fun mileageFactor(mileage: Int): Double {
        return when (mileage) {
            in 0..5_000 -> 0.5
            in 5_001..10_000 -> 1.0
            in 10_001..20_000 -> 1.5
            else -> 2.0
        }
    }
}
