package com.richert.insurance.database

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

    @ConfigProperty(name = "quarkus.http.port")
    private lateinit var httpPort: String

    @ConfigProperty(name = "quarkus.http.ssl-port")
    private lateinit var sslPort: Optional<String>

    /**
     * Initializes the CSV import process after application startup and logs
     * the startup URLs to the console.
     */
    @PostConstruct
    fun initialize() {
        performCsvImport()
        logStartup()
    }

    private fun logStartup() {
        logger.info("Application started successfully.")
        var startupMessage = "Running on http://localhost:$httpPort"
        if (sslPort.isPresent) {
            startupMessage += " and https://localhost:${sslPort.get()}"
        }
        logger.info(startupMessage)
    }

    private fun performCsvImport() {
        logger.info("CSV import enabled: ${importEnabled.orElse(false)}")
        if (!importEnabled.get()) return

        logger.info("Starting CSV import...")
        importCsvData()
    }

    /**
     * Main logic for importing all `.csv` files in the configured import folder.
     * Skips files that are already registered in the `DatabaseMigration` table.
     *
     * Each file is:
     * - parsed using `GenericCsvImporterSupport`,
     * - passed to an injected `CsvImporter`,
     * - marked as imported via a `DatabaseMigration` record.
     *
     * If a file has already been imported (based on its name), it is skipped.
     * @see GenericCsvImporterSupport
     * @see CsvImporter
     */
    @Transactional(REQUIRED)
    fun importCsvData() {

        val folderFile = File(folder)
        if (!folderFile.exists() || !folderFile.isDirectory) {
            throw IllegalStateException("Folder not found or not a directory: $folder")
        }
        val files = folderFile.listFiles { f -> f.extension == "csv" } ?: return

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
