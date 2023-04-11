package ru.mylabs.mylabsbackend.service.userService

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import ru.mylabs.mylabsbackend.model.dto.exception.BadCredentialsException
import ru.mylabs.mylabsbackend.model.dto.exception.ResourceNotFoundException
import ru.mylabs.mylabsbackend.model.dto.request.ChangeRoleRequest
import ru.mylabs.mylabsbackend.model.dto.request.ResetPasswordRequest
import ru.mylabs.mylabsbackend.model.dto.request.UserRequest
import ru.mylabs.mylabsbackend.model.entity.User
import ru.mylabs.mylabsbackend.model.entity.userRoles.UserRole
import ru.mylabs.mylabsbackend.model.entity.userRoles.UserRoleType
import ru.mylabs.mylabsbackend.model.repository.UserRepository
import ru.mylabs.mylabsbackend.service.crudService.CrudServiceImpl
import java.util.*

@Service
class UserServiceImpl(
    override val repository: UserRepository, private val passwordEncoder: BCryptPasswordEncoder
) : UserService, CrudServiceImpl<UserRequest, User, Long, UserRepository>(
    User::class.simpleName
) {
    override fun create(request: UserRequest): User {
        val model = request.asModel()
        model.uPassword = passwordEncoder.encode(request.password)
        return repository.save(model)
    }


    override fun findByLogin(login: String): User = repository.findByEmail(login).orElseThrow {
        ResourceNotFoundException("User not found")
    }

    override fun loadUserByUsername(login: String): UserDetails {
        return findByLogin(login)
    }

    override fun giveRole(id: Long, roleRequest: ChangeRoleRequest): User {
        val user: User = repository.findById(id).orElseThrow { ResourceNotFoundException("User not found") }
        user.takeIf { !it.containsRole(it, roleRequest) }?.roles?.add(UserRole(roleRequest.name))
        return repository.save(user)
    }

    override fun deleteRole(id: Long, roleRequest: ChangeRoleRequest): User {
        val user: User = repository.findById(id).orElseThrow { ResourceNotFoundException("User not found") }
        if (roleRequest.name != UserRoleType.USER)
            user.removeRole(user, roleRequest)
        else throw BadCredentialsException()
        return repository.save(user)
    }

   override fun update(resetPasswordRequest: ResetPasswordRequest): User {
       resetPasswordRequest.newPassword = passwordEncoder.encode(resetPasswordRequest.newPassword)
       var user = convertToNonOptional(repository.findByEmail(resetPasswordRequest.email))
       user.uPassword = resetPasswordRequest.newPassword
       return repository.save(user)
    }

    override fun convertToNonOptional(user: Optional<User>): User {
        return user.toNullable() ?: throw ResourceNotFoundException()
    }

    fun <T : Any> Optional<T>.toNullable(): T? = this.orElse(null)

}