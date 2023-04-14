package ru.mylabs.mylabsbackend.model.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mylabs.mylabsbackend.model.entity.Property
import java.util.*

@Repository
interface PropertiesRepository : CrudRepository<Property, Long>{
    fun findById(id: String): Optional<Property>
}