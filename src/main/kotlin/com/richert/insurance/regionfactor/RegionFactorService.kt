package com.richert.insurance.regionfactor

import com.richert.insurance.regionfactor.model.RegionFactorDTO
import com.richert.insurance.utils.toDTO
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class RegionFactorService {

    @Inject
    private lateinit var regionFactorRepository: RegionFactorRepository

    fun getAll(): List<RegionFactorDTO> {
        return regionFactorRepository.listAll().map { it.toDTO() }
    }
}