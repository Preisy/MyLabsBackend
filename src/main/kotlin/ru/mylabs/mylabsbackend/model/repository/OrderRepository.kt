package ru.mylabs.mylabsbackend.model.repository

import org.springframework.data.domain.Sort
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mylabs.mylabsbackend.model.entity.Order

@Repository
interface OrderRepository : CrudRepository<Order, Long>
{
    fun findByUserId(userId: Long): Iterable<Order>
}