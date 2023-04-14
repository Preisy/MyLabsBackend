package ru.mylabs.mylabsbackend.model.dto.request

import ru.mylabs.mylabsbackend.model.entity.Property

class ReferralPercentageRequest(
    val percent: String
    )
{
        fun asModel() = Property("referral_percentage", percent)
}