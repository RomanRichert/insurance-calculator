package com.scopevisio.insurance.database

import org.jboss.logging.Logger
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object GenericCsvImporterSupport {

    private val logger: Logger = Logger.getLogger(javaClass.name)

    fun parseCsv(input: InputStream): List<Map<String, String>> {
        val reader = BufferedReader(InputStreamReader(input))
        val lines = reader.readLines()

        if (lines.isEmpty()) throw IllegalArgumentException("CSV is empty")

        val header = lines.first().split("\t").map { it.trim() }

        return lines.drop(1).mapNotNull { line ->
            val values = line.split("\t").map { it.trim() }
            if (values.size != header.size) {
                logger.warn("Skipping malformed line: $line")
                null
            } else {
                header.zip(values).toMap()
            }
        }
    }
}