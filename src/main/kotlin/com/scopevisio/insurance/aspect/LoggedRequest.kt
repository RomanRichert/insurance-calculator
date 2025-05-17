package com.scopevisio.insurance.aspect

import jakarta.interceptor.InterceptorBinding

@MustBeDocumented
@InterceptorBinding
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class LoggedRequest
