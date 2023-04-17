package ru.mylabs.mylabsbackend.service.userService

import org.springframework.security.core.userdetails.UserDetailsService
import ru.mylabs.mylabsbackend.model.dto.request.ChangeRoleRequest
import ru.mylabs.mylabsbackend.model.dto.request.ResetPasswordRequest
import ru.mylabs.mylabsbackend.model.dto.request.UserRequest
import ru.mylabs.mylabsbackend.model.entity.Property
import ru.mylabs.mylabsbackend.model.entity.User
import ru.mylabs.mylabsbackend.service.crudService.CrudService
import java.util.*

interface UserService : CrudService<UserRequest, User, Long>, UserDetailsService {
    fun findByLogin(login: String): User
    fun giveRole(id: Long, roleRequest: ChangeRoleRequest): User
    fun deleteRole(id: Long, roleRequest: ChangeRoleRequest): User
    fun update(resetPasswordRequest: ResetPasswordRequest): User
    fun creditPercent(labPrice: Int, user: User): User
    override fun findById(id: Long): User
    fun getInvitedUsers(id: Long):  MutableList<User>
}