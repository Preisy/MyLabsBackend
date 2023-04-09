package ru.mylabs.mylabsbackend.model.repository

import org.springframework.data.domain.Sort
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mylabs.mylabsbackend.model.entity.labs.Lab

@Repository
interface LabRepository : CrudRepository<Lab, Long> {
    fun findAll(sort: Sort): Iterable<Lab>
}