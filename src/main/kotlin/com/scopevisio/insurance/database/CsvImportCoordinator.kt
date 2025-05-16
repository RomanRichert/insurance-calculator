package com.scopevisio.insurance.database

import io.quarkus.runtime.Startup
import jakarta.annotation.PostConstruct
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Instance
import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import jakarta.transaction.Transactional.TxType.REQUIRED
import org.jboss.logging.Logger
import java.io.File

@Startup
@ApplicationScoped
class CsvImportCoordinator {

    private val logger: Logger = Logger.getLogger(javaClass.name)

    @Inject
    private lateinit var importers: Instance<CsvImporter>

    @Inject
    private lateinit var entityManager: EntityManager

    private val folder = "/data"

    @Transactional(REQUIRED)
    fun ensureUuidExtension() {
        entityManager.createNativeQuery("""CREATE EXTENSION IF NOT EXISTS "uuid-ossp";""").executeUpdate()
        logger.info("UUID extension ensured.")
    }

    @PostConstruct
    fun run() {
        ensureUuidExtension()

        val folderURL = javaClass.getResource(folder)
            ?: throw IllegalStateException("Folder not found: $folder")

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

            importers.get().import(rows)

            val migration = DatabaseMigration(file.name)
            entityManager.persist(migration)
            logger.info("Imported: ${file.name}")
        }
    }
}
