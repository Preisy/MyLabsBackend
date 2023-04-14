package ru.mylabs.mylabsbackend.service.propertiesService

import ru.mylabs.mylabsbackend.model.dto.request.LabsQuantityRequest
import ru.mylabs.mylabsbackend.model.dto.request.ReferralPercentageRequest
import ru.mylabs.mylabsbackend.model.entity.Property

interface PropertiesService {
    fun getQuantity(): Property
    fun setQuantity(quantity: LabsQuantityRequest): Property
    fun getPercent(): Property
    fun setPercent(newPercent: ReferralPercentageRequest): Property
}