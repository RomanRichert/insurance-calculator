package com.scopevisio.insurance.utils

import io.vertx.core.json.JsonObject
import org.jboss.logging.Logger

fun <T> T.toJson(): JsonObject {
    return try {
        JsonObject.mapFrom(this)
    } catch (e: Exception) {
        Logger.getLogger(this!!::class.java.name).warn("Error while parsing JSON: ${e.message} Trying cast to String.")
        JsonObject(this as String)
    }
}

fun <T> T.toPrettyJson(): String {
    return this.toJson().encodePrettily()
}