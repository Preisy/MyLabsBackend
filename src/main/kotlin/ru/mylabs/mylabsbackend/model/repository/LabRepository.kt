package ru.mylabs.mylabsbackend.model.repository

import org.springframework.data.domain.Sort
import org.springframework.data.repository.CrudRepository
import ru.mylabs.mylabsbackend.model.entity.labs.Lab

interface LabRepository : CrudRepository<Lab, Long> {
    fun findAll(sort: Sort): Iterable<Lab>
}