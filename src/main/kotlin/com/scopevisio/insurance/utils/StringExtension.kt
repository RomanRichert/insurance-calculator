package com.scopevisio.insurance.utils

/**
 * Extension function for nullable Strings:
 * Returns this string if it's not null or blank; otherwise, returns the provided default string
 */
infix fun String?.or(string: String?): String? {
    return if (this.isNullOrBlank()) string
    else this
}

infix fun String?.orThrow(e: Exception): String {
    if (this.isNullOrBlank()) throw e
    else return this
}