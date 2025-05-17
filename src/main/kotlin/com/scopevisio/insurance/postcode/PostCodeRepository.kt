package com.scopevisio.insurance.postcode

import com.scopevisio.insurance.postcode.model.PostCode
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class PostCodeRepository : PanacheRepository<PostCode> {

    fun findFiltered(
        postalCode: String?,
        state: String?,
        region: String?,
        district: String?,
        city: String?,
        quarter: String?,
        page: Int,
        size: Int
    ): List<PostCode> {
        val query = StringBuilder("1=1")
        val params = mutableMapOf<String, Any>()

        fun append(field: String, value: String?) {
            if (!value.isNullOrBlank()) {
                query.append(" AND $field LIKE :$field")
                params[field] = "%$value%"
            }
        }

        append("postalCode", postalCode)
        append("state", state)
        append("region", region)
        append("district", district)
        append("city", city)
        append("quarter", quarter)

        return find(query.toString(), params)
            .page(page, size)
            .list()
    }
}