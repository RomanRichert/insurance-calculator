package com.scopevisio.insurance.exception

import org.eclipse.microprofile.openapi.annotations.media.Schema
import java.time.Instant

@Schema(name = "ErrorResponse", description = "Error response structure for REST exceptions")
data class ErrorResponse(
    val status: Int,
    val error: String,
    val message: String?,
    val cause: String?,
    val timestamp: String = Instant.now().toString()
)