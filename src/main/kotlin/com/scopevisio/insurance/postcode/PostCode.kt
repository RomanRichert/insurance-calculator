package com.scopevisio.insurance.postcode

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "post_code")
class PostCode : PanacheEntity() {

    @Column(name = "postal_code", nullable = false, unique = true)
    lateinit var postalCode: String

    @Column(name = "state", nullable = false) // region1
    lateinit var state: String

    @Column(name = "region")                 // region2
    var region: String? = null

    @Column(name = "district")
    var district: String? = null        // region 3

    @Column(name = "city")
    var city: String? = null            // region 4

    @Column(name = "quarter")                // area1
    var quarter: String? = null
}