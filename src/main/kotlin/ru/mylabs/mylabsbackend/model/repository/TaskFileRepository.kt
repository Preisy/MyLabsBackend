package ru.mylabs.mylabsbackend.model.repository

import org.springframework.data.repository.CrudRepository
import ru.mylabs.mylabsbackend.model.entity.TaskFile

interface TaskFileRepository : CrudRepository<TaskFile, Long>