package ru.mylabs.mylabsbackend.service.meService

import ru.mylabs.mylabsbackend.model.dto.request.MeRequest
import ru.mylabs.mylabsbackend.model.dto.request.UserRequest
import ru.mylabs.mylabsbackend.model.entity.User

interface MeService {
    fun getMeInfo(): User
    fun putMeInfo(meRequest: MeRequest): User
}