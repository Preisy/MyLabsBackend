package ru.mylabs.mylabsbackend.model.repository

import org.springframework.data.repository.CrudRepository
import ru.mylabs.mylabsbackend.model.entity.Order

interface OrderRepository : CrudRepository<Order, Long>