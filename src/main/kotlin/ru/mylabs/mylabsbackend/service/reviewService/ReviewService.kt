package ru.mylabs.mylabsbackend.service.reviewService

import ru.mylabs.mylabsbackend.model.dto.response.reviewResponse.Items
import ru.mylabs.mylabsbackend.model.dto.response.userInfoResponse.UserInfoResponse

interface ReviewService {
    fun getReviews(): Iterable<Items>
    fun getUserInfo(ids: String?): UserInfoResponse?
}