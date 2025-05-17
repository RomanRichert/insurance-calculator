package com.scopevisio.insurance.regionfactor.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "region_factor")
class RegionFactor : PanacheEntityBase {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = IDENTITY)
    var id: Long? = null

    @Column(name = "state", nullable = false)
    lateinit var state: String

    @Column(name = "factor", nullable = false)
    var factor: Double = 1.0
}
