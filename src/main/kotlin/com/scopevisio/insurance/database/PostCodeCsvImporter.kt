package com.scopevisio.insurance.database

import com.scopevisio.insurance.postcode.PostCode
import com.scopevisio.insurance.utils.or
import com.scopevisio.insurance.utils.orThrow
import jakarta.enterprise.context.ApplicationScoped
import org.jboss.logging.Logger

@ApplicationScoped
class PostCodeCsvImporter : CsvImporter {

    private val logger: Logger = Logger.getLogger(javaClass.name)

    private val requiredFields = listOf(
        "POSTLEITZAHL", "REGION1", "REGION2", "REGION3", "REGION4", "AREA1"
    )

    override fun import(rows: List<Map<String, String>>) {
        val missing = requiredFields.filter { field -> rows.firstOrNull()?.containsKey(field) != true }
        if (missing.isNotEmpty()) throw IllegalArgumentException("Missing required CSV fields: $missing")

        var count = 0
        for (row in rows) {
            val postCode = PostCode().apply {
                postalCode =
                    row["POSTLEITZAHL"] orThrow IllegalArgumentException("Missing required field: POSTLEITZAHL")
                state = row["REGION1"] orThrow IllegalArgumentException("Missing required field: REGION1")
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
}