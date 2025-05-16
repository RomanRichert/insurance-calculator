package com.scopevisio.insurance.regionfactor

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "region_factor")
class RegionFactor : PanacheEntity() {

    @Column(nullable = false, unique = true)
    lateinit var state: String

    @Column(nullable = false)
    var factor: Double = 1.0
}
