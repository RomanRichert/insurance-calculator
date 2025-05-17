package com.scopevisio.insurance.database

import com.scopevisio.insurance.PostgresTestResource
import com.scopevisio.insurance.postcode.PostCodeRepository
import io.quarkus.test.TestTransaction
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.TestProfile
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Paths

@QuarkusTest
@TestProfile(NoImportProfile::class)
@QuarkusTestResource(PostgresTestResource::class)
class PostCodeCsvImporterTest {

    @Inject
    private lateinit var importer: PostCodeCsvImporter

    @Inject
    private lateinit var postCodeRepository: PostCodeRepository

    private lateinit var testFile: File

    @BeforeEach
    fun setup() {
        val resourceUrl = javaClass.classLoader.getResource("data/test-postcodes.csv")
        requireNotNull(resourceUrl) { "Test CSV file not found." }
        testFile = Paths.get(resourceUrl.toURI()).toFile()
    }

    @Test
    @TestTransaction
    fun importTest() {
        val rows = GenericCsvImporterSupport.parseCsv(testFile.inputStream())

        importer.import(rows)

        val imported = postCodeRepository.listAll()
        assertEquals(24, imported.size)

        val first = imported[0]
        assertEquals("79238", first.postalCode)
        assertEquals("Ehrenkirchen", first.city)
        assertEquals("Baden-Württemberg", first.state)
    }
}
