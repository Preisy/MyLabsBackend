package ru.mylabs.mylabsbackend.service.meService

import org.springframework.security.core.userdetails.UserDetailsService
import ru.mylabs.mylabsbackend.model.dto.request.UserRequest
import ru.mylabs.mylabsbackend.model.entity.User

interface MeService {
    fun getMeInfo(): User
    fun putMeInfo(userRequest: UserRequest): User
}