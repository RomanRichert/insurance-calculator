package com.richert.insurance.database

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.InputStream
import java.io.InputStreamReader

object GenericCsvImporterSupport {

    /**
     * The parser is configured to:
     * - use UTF-8 encoding,
     * - read headers from the first line,
     * - skip the header when processing records,
     * - ignore empty lines,
     * - trim both keys and values.
     *
     * @param input the CSV file input stream
     * @return list of parsed records as key-value maps
     */
    fun parseCsv(input: InputStream): List<Map<String, String>> {
        val reader = InputStreamReader(input, Charsets.UTF_8)

        val format = CSVFormat.Builder.create()
            .setDelimiter(',')
            .setQuote('"')
            .setHeader()
            .setSkipHeaderRecord(true)
            .setIgnoreEmptyLines(true)
            .setTrim(true)
            .build()

        val parser = CSVParser(reader, format)

        return parser.records.map { record ->
            record.toMap().mapKeys { it.key.trim() }.mapValues { it.value.trim().removeSurrounding("\"") }
        }
    }
}