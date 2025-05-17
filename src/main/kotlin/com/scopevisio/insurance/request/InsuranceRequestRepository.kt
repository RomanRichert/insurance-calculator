package com.scopevisio.insurance.request

import com.scopevisio.insurance.request.model.InsuranceRequest
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import java.util.*

@ApplicationScoped
class InsuranceRequestRepository : PanacheRepositoryBase<InsuranceRequest, UUID> {

    fun findPaged(page: Int, size: Int): List<InsuranceRequest> {
        return findAll()
            .page(page, size)
            .list()
    }
}