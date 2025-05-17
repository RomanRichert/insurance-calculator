package com.scopevisio.insurance.database

import io.quarkus.test.junit.QuarkusTestProfile

class NoImportProfile : QuarkusTestProfile {
    override fun getConfigOverrides(): Map<String, String> {
        return mapOf(
            "csv.import-enabled" to "false"
        )
    }
}
