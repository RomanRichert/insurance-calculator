package com.richert.insurance.aspect

import com.richert.insurance.utils.toListOfJsonObjects
import com.richert.insurance.utils.toPrettyJson
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import jakarta.annotation.Priority
import jakarta.interceptor.AroundInvoke
import jakarta.interceptor.Interceptor
import jakarta.interceptor.InvocationContext
import org.jboss.logging.Logger
import java.lang.System.lineSeparator

@Interceptor
@LoggedRequest
@Suppress("unused")
@Priority(Interceptor.Priority.APPLICATION)
class RequestLoggingInterceptor {

    private val logger: Logger = Logger.getLogger(javaClass.name)

    /**
     * Intercepts method execution to log entry, exit, arguments, and return value.
     *
     * - Logs method name, class, and parameters on entry.
     * - Logs methods return value on successful exit.
     * - Logs exception if thrown, and rethrows it.
     */
    @AroundInvoke
    fun log(ctx: InvocationContext): Any? {
        val method = ctx.method
        val className = ctx.target::class.simpleName
        val args = ctx.parameters.joinToString(", ") { it?.toString() ?: "null" }

        logger.info("-> Enter: $className.${method.name}($args)")

        return try {
            val result = ctx.proceed()

            logger.info("<- Exit : $className.${method.name} ->${lineSeparator()}${determineResult(result)}")
            result
        } catch (e: Exception) {
            logger.error("Exception in $className.${method.name}: ${e.message}", e)
            throw e
        }
    }

    private fun determineResult(result: Any?): String {
        return when (result) {
            null -> "null"
            is Comparable<*> -> result.toString()
            is JsonObject -> result.encodePrettily()
            is JsonArray -> result.toListOfJsonObjects().map { it.toPrettyJson() + lineSeparator() }.toString()
            is Iterable<*> -> result.map { tryToEncodeAsJson(it) }.toString()
            else -> tryToEncodeAsJson(result)
        }
    }

    private fun tryToEncodeAsJson(result: Any?): String {
        return try {
            result.toPrettyJson()
        } catch (e: Exception) {
            result.toString()
        }
    }
}
