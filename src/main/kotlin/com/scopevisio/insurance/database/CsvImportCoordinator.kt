package com.scopevisio.insurance.database

import io.quarkus.runtime.Startup
import jakarta.annotation.PostConstruct
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Instance
import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import jakarta.transaction.Transactional.TxType.REQUIRED
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.jboss.logging.Logger
import java.io.File
import java.util.*

@Startup
@ApplicationScoped
class CsvImportCoordinator {

    val logger: Logger by lazy { Logger.getLogger("CsvImportCoordinator") }

    @ConfigProperty(name = "csv.import-enabled", defaultValue = "false")
    private lateinit var importEnabled: Optional<Boolean>

    @Inject
    private lateinit var importers: Instance<CsvImporter>

    @Inject
    private lateinit var entityManager: EntityManager

    @ConfigProperty(name = "csv.import-folder", defaultValue = "/data")
    private lateinit var folder: String

    @PostConstruct
    fun initialize() {
        if (!importEnabled.get()) return
        importCsvData()
        logger.info("Application started successfully.")
    }

    @Transactional(REQUIRED)
    fun importCsvData() {

        val folderURL = javaClass.getResource(folder) ?: throw IllegalStateException("Folder not found: $folder")

        val files = File(folderURL.toURI()).listFiles { f -> f.extension == "csv" } ?: return

        for (file in files.sortedBy { it.name }) {
            val alreadyImported = entityManager.createQuery(
                "SELECT COUNT(m) FROM DatabaseMigration m WHERE m.fileName = :name", Long::class.java
            ).setParameter("name", file.name).singleResult > 0

            if (alreadyImported) {
                logger.info("Skipping already imported file: ${file.name}")
                continue
            }

            logger.info("Importing file: ${file.name}")
            val rows = file.inputStream().use { GenericCsvImporterSupport.parseCsv(it) }
            logger.debug("Parsed CSV file: ${file.name} — ${rows.size} records")

            try {
                importers.get().import(rows)
            } catch (ex: IllegalArgumentException) {
                logger.error("Error occurred while importing PostCode CSV: ${ex.message}")
                throw ex
            }

            val migration = DatabaseMigration().apply { this.fileName = file.name }
            entityManager.persist(migration)
            logger.info("Imported: ${file.name}")
        }
    }
}
