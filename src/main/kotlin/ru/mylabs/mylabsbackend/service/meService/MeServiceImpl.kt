package ru.mylabs.mylabsbackend.service.meService

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ru.mylabs.mylabsbackend.model.dto.exception.ResourceNotFoundException
import ru.mylabs.mylabsbackend.model.dto.request.MeRequest
import ru.mylabs.mylabsbackend.model.dto.request.UserRequest
import ru.mylabs.mylabsbackend.model.entity.User
import ru.mylabs.mylabsbackend.model.repository.UserRepository


@Service
class MeServiceImpl(
    val repository: UserRepository
) : MeService {
    override fun getMeInfo(): User {
        val auth: Authentication = SecurityContextHolder.getContext().authentication
        val user = repository.findByEmail(auth.name).orElseThrow {
            ResourceNotFoundException("User")
        }
        return user
    }

    override fun putMeInfo(meRequest: MeRequest): User {
        val user = getMeInfo()
        user.uname = meRequest.name
        user.contact = meRequest.contact
        return repository.save(user)
    }
}