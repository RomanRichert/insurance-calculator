package com.richert.insurance.postcode.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "post_code")
class PostCode : PanacheEntityBase {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = IDENTITY)
    var id: Long? = null

    @Column(name = "postal_code", nullable = false)
    lateinit var postalCode: String

    @Column(name = "state", nullable = false)
    lateinit var state: String

    @Column(name = "region")
    var region: String? = null

    @Column(name = "district")
    var district: String? = null

    @Column(name = "city")
    var city: String? = null

    @Column(name = "quarter")
    var quarter: String? = null
}