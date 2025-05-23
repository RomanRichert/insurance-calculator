package com.richert.insurance.exception

import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.jboss.logging.Logger

@Provider
class GlobalExceptionHandler : ExceptionMapper<Throwable> {

    private val logger: Logger = Logger.getLogger(GlobalExceptionHandler::class.java)

    override fun toResponse(exception: Throwable): Response {
        val status = when (exception) {
            is WebApplicationException -> exception.response.status
            is IllegalArgumentException -> Response.Status.BAD_REQUEST.statusCode
            else -> Response.Status.INTERNAL_SERVER_ERROR.statusCode
        }

        val error = Response.Status.fromStatusCode(status).reasonPhrase
        val message = exception.message ?: error
        val cause = exception.cause?.message

        if (status >= 500) {
            logger.error("Internal error", exception)
        } else {
            logger.warn("Client error: $message")
        }

        val body = ErrorResponse(
            status = status,
            error = error,
            message = message,
            cause = cause
        )

        return Response.status(status).entity(body).build()
    }
}
