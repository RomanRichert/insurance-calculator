package com.richert.insurance.database

import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

@QuarkusTest
class GenericCsvImporterSupportTest {

    @Test
    fun parseCsvWithHeadersAndTrimValuesTest() {

        val csvContent = """
            "Name","Age","City"
            " Alice ","30"," Berlin "
            "Bob","25","Munich"
        """.trimIndent()

        val inputStream = ByteArrayInputStream(csvContent.toByteArray(Charsets.UTF_8))

        val result = GenericCsvImporterSupport.parseCsv(inputStream)

        assertEquals(2, result.size)

        assertEquals(mapOf("Name" to "Alice", "Age" to "30", "City" to "Berlin"), result[0])
        assertEquals(mapOf("Name" to "Bob", "Age" to "25", "City" to "Munich"), result[1])
    }

    @Test
    fun parseCsvWithEmptyLinesTest() {

        val csvContent = """
            "Col1","Col2"
            "A1","B1"
            
            "A2","B2"
        """.trimIndent()

        val inputStream = ByteArrayInputStream(csvContent.toByteArray(Charsets.UTF_8))

        val result = GenericCsvImporterSupport.parseCsv(inputStream)

        assertEquals(2, result.size)
        assertEquals("A1", result[0]["Col1"])
        assertEquals("A2", result[1]["Col1"])
    }
}