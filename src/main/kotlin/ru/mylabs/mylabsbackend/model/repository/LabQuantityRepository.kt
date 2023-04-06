package ru.mylabs.mylabsbackend.model.repository

import org.springframework.data.repository.CrudRepository
import ru.mylabs.mylabsbackend.model.entity.labs.LabsQuantity

interface LabQuantityRepository : CrudRepository<LabsQuantity, Long>