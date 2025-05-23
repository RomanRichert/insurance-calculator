package com.richert.insurance.regionfactor

import com.richert.insurance.regionfactor.model.RegionFactor
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RegionFactorRepository : PanacheRepository<RegionFactor>