package ru.mylabs.mylabsbackend.model.repository

import org.springframework.data.repository.CrudRepository
import ru.mylabs.mylabsbackend.model.entity.Promocode
import java.util.*

interface PromocodeRepository: CrudRepository<Promocode, Long> {
    fun findByPromoName(promoName: String): Optional<Promocode>
}