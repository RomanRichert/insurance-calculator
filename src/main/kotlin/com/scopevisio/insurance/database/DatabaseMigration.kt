package com.scopevisio.insurance.database

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "database_migration")
class DatabaseMigration : PanacheEntityBase {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
    lateinit var id: UUID

    @Column(name = "file_name", nullable = false, unique = true)
    lateinit var fileName: String

    @Suppress("unused")
    @Column(name = "applied_at", nullable = false)
    var appliedAt: LocalDateTime = LocalDateTime.now()
}