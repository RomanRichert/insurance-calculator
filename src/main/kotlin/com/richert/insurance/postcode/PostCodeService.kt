package com.richert.insurance.postcode

import com.richert.insurance.postcode.model.PostCode
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class PostCodeService {

    @Inject
    private lateinit var postCodeRepository: PostCodeRepository

    fun getFiltered(
        postalCode: String?,
        state: String?,
        region: String?,
        district: String?,
        city: String?,
        quarter: String?,
        page: Int,
        size: Int
    ): List<PostCode> {
        return postCodeRepository.findFiltered(postalCode, state, region, district, city, quarter, page, size)
    }
}