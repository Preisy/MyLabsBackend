package ru.mylabs.mylabsbackend.service.userService

import org.springframework.security.core.userdetails.UserDetailsService
import ru.mylabs.mylabsbackend.model.dto.exception.ResourceNotFoundException
import ru.mylabs.mylabsbackend.model.dto.request.ChangeRoleRequest
import ru.mylabs.mylabsbackend.model.dto.request.UserRequest
import ru.mylabs.mylabsbackend.model.entity.User
import ru.mylabs.mylabsbackend.model.entity.UserRole
import ru.mylabs.mylabsbackend.model.entity.UserRoleType
import ru.mylabs.mylabsbackend.service.crudService.CrudService

interface UserService : CrudService<UserRequest, User, Long>, UserDetailsService {
    fun findByLogin(login: String): User
    fun giveRole(id: Long): User
}