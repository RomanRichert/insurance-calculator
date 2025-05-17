package com.scopevisio.insurance.aspect

import com.scopevisio.insurance.utils.toPrettyJson
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

    @AroundInvoke
    fun log(ctx: InvocationContext): Any? {
        val method = ctx.method
        val className = ctx.target::class.simpleName
        val args = ctx.parameters.joinToString(", ") { it?.toString() ?: "null" }

        logger.info("-> Enter: $className.${method.name}($args)")

        return try {
            val result = ctx.proceed()
            val resultAsJson = try {
                result.toPrettyJson()
            } catch (e: Exception) {
                logger.warn("Could not encode result as JSON: ${e.message}")
                result
            }

            logger.info("<- Exit : $className.${method.name} ->${lineSeparator()}$resultAsJson")
            result
        } catch (e: Exception) {
            logger.error("Exception in $className.${method.name}: ${e.message}", e)
            throw e
        }
    }
}
