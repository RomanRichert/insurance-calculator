package com.scopevisio.insurance.postcode

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class PostCodeRepository : PanacheRepository<PostCode>