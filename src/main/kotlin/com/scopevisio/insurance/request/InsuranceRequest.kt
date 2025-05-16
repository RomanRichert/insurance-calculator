package com.scopevisio.insurance.request

import com.scopevisio.insurance.vehicle.VehicleType
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "insurance_request")
class InsuranceRequest : PanacheEntityBase {

    @Id
    @GeneratedValue
    var id: UUID? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false)
    lateinit var vehicleType: VehicleType

    @Column(name = "postal_code", nullable = false)
    lateinit var postalCode: String

    @Column(name = "annual_mileage", nullable = false)
    var annualMileage: Int = 0

    @Column(name = "calculated_premium", nullable = false)
    var calculatedPremium: Double = 0.0

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
}