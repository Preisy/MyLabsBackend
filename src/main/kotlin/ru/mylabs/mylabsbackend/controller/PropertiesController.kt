package ru.mylabs.mylabsbackend.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.mylabs.mylabsbackend.model.dto.request.LabsQuantityRequest
import ru.mylabs.mylabsbackend.model.dto.request.ReferralPercentageRequest
import ru.mylabs.mylabsbackend.model.entity.Property
import ru.mylabs.mylabsbackend.service.propertiesService.PropertiesService

@RestController
class PropertiesController(
    private val propertiesService: PropertiesService
) {

    @PutMapping("/labs/quantity")
    fun setQuantity(@RequestBody labsQuantityRequest: LabsQuantityRequest): Property {
        return propertiesService.setQuantity(labsQuantityRequest)
    }
    @GetMapping("/properties/percent")
    fun getPercent(): Property {
        return propertiesService.getPercent()
    }
    @PutMapping("/properties/percent")
    fun setPercent(@RequestBody referralPercentageRequest: ReferralPercentageRequest): Property {
        return propertiesService.setPercent(referralPercentageRequest)
    }
}