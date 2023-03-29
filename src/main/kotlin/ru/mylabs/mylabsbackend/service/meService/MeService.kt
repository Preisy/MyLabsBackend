package ru.mylabs.mylabsbackend.service.meService

import org.springframework.security.core.userdetails.UserDetailsService
import ru.mylabs.mylabsbackend.model.entity.User

interface MeService {
    fun getMeInfo(): User
}