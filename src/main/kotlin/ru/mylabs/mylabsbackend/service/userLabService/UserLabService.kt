package ru.mylabs.mylabsbackend.service.userLabService


import ru.mylabs.mylabsbackend.model.dto.request.UserLabRequest
import ru.mylabs.mylabsbackend.model.entity.labs.UserLab

interface UserLabService {
    fun create(userLabRequest: UserLabRequest): UserLab
    fun findByUserId(offset: Int? = null, limit: Int? = null): Iterable<UserLab>
    fun findById(id: Long): UserLab
    fun update(id: Long, userLabRequest: UserLabRequest): UserLab
    fun delete(id: Long)
}