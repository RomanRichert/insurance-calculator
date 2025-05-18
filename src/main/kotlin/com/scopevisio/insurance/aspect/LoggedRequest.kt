package com.scopevisio.insurance.aspect

import jakarta.interceptor.InterceptorBinding

/**
 * Marks a class or method for request logging interception.
 *
 * This annotation binds the `RequestLoggingInterceptor`, which logs:
 * - method entry and exit,
 * - parameters,
 * - return value or exception.
 *
 * Can be applied at the class or method level.
 *
 * Usage:
 * ```
 * @LoggedRequest
 * fun someBusinessMethod() { ... }
 * ```
 * @see RequestLoggingInterceptor
 */
@MustBeDocumented
@InterceptorBinding
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class LoggedRequest