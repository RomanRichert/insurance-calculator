package com.scopevisio.insurance.regionfactor

import com.scopevisio.insurance.regionfactor.model.RegionFactorDTO
import com.scopevisio.insurance.utils.toDTO
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