package com.scopevisio.insurance.database

import jakarta.inject.Inject
import org.jboss.logging.Logger
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import java.io.File
import java.util.concurrent.Callable

@Suppress("unused")
@Command(
    name = "import-postcodes",
    description = ["Import a CSV file into the post_code table"]
)
class PostCodeCsvCommand : Callable<Int> {

    private val logger: Logger = Logger.getLogger(javaClass.name)

    @Inject
    lateinit var importer: PostCodeCsvImporter

    @Parameters(index = "0", description = ["Path to the CSV file"])
    lateinit var csvPath: String

    override fun call(): Int {
        val file = File(csvPath)
        if (!file.exists() || !file.isFile) {
            logger.error("File not found: $csvPath")
            return 1
        }

        val rows = file.inputStream().use {
            GenericCsvImporterSupport.parseCsv(it)
        }

        importer.import(rows)

        logger.info("Manual import completed: $csvPath")
        return 0
    }
}
