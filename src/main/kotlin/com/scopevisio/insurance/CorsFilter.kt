package com.scopevisio.insurance

import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerResponseContext
import jakarta.ws.rs.container.ContainerResponseFilter
import jakarta.ws.rs.ext.Provider
import java.io.IOException

/**
 * JAX-RS response filter that appends CORS headers to every HTTP response.
 *
 * This filter ensures that cross-origin requests from web frontends (e.g., running on localhost:3000)
 * are accepted and properly handled by the backend, by explicitly allowing required headers and methods.
 *
 * Note:
 * - It enables credentials (`Access-Control-Allow-Credentials`) and defines common HTTP methods as allowed.
 */
@Provider
class CorsFilter : ContainerResponseFilter {

    @Throws(IOException::class)
    override fun filter(
        requestContext: ContainerRequestContext?,
        responseContext: ContainerResponseContext
    ) {
        responseContext.headers.add("Access-Control-Allow-Origin", "*")
        responseContext.headers.add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
        responseContext.headers.add("Access-Control-Allow-Credentials", "true")
        responseContext.headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
    }
}
