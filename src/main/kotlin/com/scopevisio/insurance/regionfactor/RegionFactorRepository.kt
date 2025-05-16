package com.scopevisio.insurance.regionfactor

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RegionFactorRepository : PanacheRepository<RegionFactor>