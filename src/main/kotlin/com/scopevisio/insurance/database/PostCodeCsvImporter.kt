package com.scopevisio.insurance.database

import com.scopevisio.insurance.postcode.model.PostCode
import com.scopevisio.insurance.utils.or
import com.scopevisio.insurance.utils.orThrow
import jakarta.enterprise.context.ApplicationScoped
import org.jboss.logging.Logger

@ApplicationScoped
class PostCodeCsvImporter : CsvImporter {

    private val logger: Logger = Logger.getLogger(javaClass.name)

    // Required CSV columns that must be present in the header
    private val requiredFields = listOf(
        "POSTLEITZAHL", "REGION1", "REGION2", "REGION3", "REGION4", "AREA1"
    )

    /**
     * Imports a list of rows parsed from a CSV file into the database as PostCode entities.
     *
     * - Validates the presence of required headers.
     * - Converts each row into a [PostCode] instance.
     * - Persists the instance into the database.
     * - Skips invalid records with a warning log.
     *
     * @param rows the parsed CSV data, each row as a Map of column name to value
     * @throws IllegalArgumentException if required headers are missing
     */
    override fun import(rows: List<Map<String, String>>) {
        logger.debug("CSV headers: ${rows.firstOrNull()?.keys}")
        validateRequiredFields(rows)

        var count = 0
        for (row in rows) {
            val postCode = PostCode().apply {
                // Mandatory fields
                postalCode =
                    row["POSTLEITZAHL"] orThrow IllegalArgumentException("Missing required field: POSTLEITZAHL")
                state = row["REGION1"] orThrow IllegalArgumentException("Missing required field: REGION1")
                // Optional fields
                region = row["REGION2"] or null
                district = row["REGION3"] or null
                city = row["REGION4"] or null
                quarter = row["AREA1"] or null
            }

            try {
                postCode.persist()
                count++
            } catch (ex: Exception) {
                logger.warn("Skipping invalid record: ${postCode.postalCode} — ${ex.message}")
            }
        }

        logger.info("Imported $count postcodes.")
    }

    private fun validateRequiredFields(rows: List<Map<String, String>>) {
        val missing = requiredFields.filter { field -> rows.firstOrNull()?.containsKey(field) != true }
        if (missing.isNotEmpty()) {
            val headers = rows.firstOrNull()?.keys?.joinToString(", ") ?: "NO ROWS"
            logger.error("CSV header: $headers")
            throw IllegalArgumentException("Missing required CSV fields: $missing")
        }
    }
}