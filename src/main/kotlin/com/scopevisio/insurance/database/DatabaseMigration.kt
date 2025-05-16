package com.scopevisio.insurance.database

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "database_migration")
class DatabaseMigration(
    @Column(name = "file_name", nullable = false, unique = true)
    var fileName: String,

    @Column(name = "applied_at", nullable = false)
    var appliedAt: LocalDateTime = LocalDateTime.now()
) : PanacheEntityBase {

    @Id
    @GeneratedValue
    var id: UUID? = null
}