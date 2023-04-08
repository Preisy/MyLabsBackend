package ru.mylabs.mylabsbackend.model.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mylabs.mylabsbackend.model.entity.labs.LabsQuantity
@Repository
interface LabQuantityRepository : CrudRepository<LabsQuantity, Long>