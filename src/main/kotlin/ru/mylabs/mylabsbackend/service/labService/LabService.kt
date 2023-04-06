package ru.mylabs.mylabsbackend.service.labService

import ru.mylabs.mylabsbackend.model.entity.labs.Lab
import ru.mylabs.mylabsbackend.model.entity.labs.LabsQuantity
import ru.mylabs.mylabsbackend.model.dto.request.LabRequest
import ru.mylabs.mylabsbackend.model.dto.request.LabsQuantityRequest

interface LabService {
    fun create(labRequest: LabRequest): Lab
    fun findAll(offset: Int? = null, limit: Int? = null): Iterable<Lab>
    fun findById(id: Long): Lab
    fun update(id: Long, labRequest: LabRequest): Lab
    fun delete(id: Long)
    fun getQuantity(): LabsQuantity
    fun setQuantity(quantity: LabsQuantityRequest): LabsQuantity
}