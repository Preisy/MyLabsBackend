package ru.mylabs.mylabsbackend.service.promocodeService

import ru.mylabs.mylabsbackend.model.entity.Promocode

interface PromocodeService {
    fun create(promoName: String): Promocode
    fun delete(id: Long)
    fun findAll(): Iterable<Promocode>
}