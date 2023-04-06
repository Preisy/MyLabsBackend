package ru.mylabs.mylabsbackend.service.userService

import org.springframework.security.core.userdetails.UserDetailsService
import ru.mylabs.mylabsbackend.model.dto.request.ChangeRoleRequest
import ru.mylabs.mylabsbackend.model.dto.request.UserRequest
import ru.mylabs.mylabsbackend.model.entity.User
import ru.mylabs.mylabsbackend.service.crudService.CrudService

interface UserService : CrudService<UserRequest, User, Long>, UserDetailsService {
    fun findByLogin(login: String): User
    fun giveRole(id: Long, roleRequest: ChangeRoleRequest): User
    fun deleteRole(id: Long, roleRequest: ChangeRoleRequest): User
}