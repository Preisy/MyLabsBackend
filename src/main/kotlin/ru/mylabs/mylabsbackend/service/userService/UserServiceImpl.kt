package ru.mylabs.mylabsbackend.service.userService

import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import ru.mylabs.mylabsbackend.model.dto.exception.BadCredentialsException
import ru.mylabs.mylabsbackend.model.dto.exception.InternalServerErrorException
import ru.mylabs.mylabsbackend.model.dto.exception.ResourceNotFoundException
import ru.mylabs.mylabsbackend.model.dto.request.ChangeRoleRequest
import ru.mylabs.mylabsbackend.model.dto.request.ResetPasswordRequest
import ru.mylabs.mylabsbackend.model.dto.request.UserRequest
import ru.mylabs.mylabsbackend.model.dto.response.InvitedUserResponse
import ru.mylabs.mylabsbackend.model.entity.User
import ru.mylabs.mylabsbackend.model.entity.userRoles.UserRole
import ru.mylabs.mylabsbackend.model.entity.userRoles.UserRoleType
import ru.mylabs.mylabsbackend.model.repository.UserPhotoRepository
import ru.mylabs.mylabsbackend.model.repository.UserRepository
import ru.mylabs.mylabsbackend.service.crudService.CrudServiceImpl
import ru.mylabs.mylabsbackend.service.meService.MeService
import ru.mylabs.mylabsbackend.service.propertiesService.PropertiesService
import java.io.File

@Service("UserService")
class UserServiceImpl(
    override val repository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val propertiesService: PropertiesService,
    private val meService: MeService,
    private val userPhotoRepository: UserPhotoRepository
) : UserService, CrudServiceImpl<UserRequest, User, Long, UserRepository>(
    User::class.simpleName
) {
    private val uploadsFolderPath = File("src/main/resources/uploads")
    private val logger = LoggerFactory.getLogger(UserServiceImpl::class.java)
    override fun findById(id: Long): User = repository.findById(id).orElseThrow {
        logger.info("User nor found")
        ResourceNotFoundException("User")
    }

    override fun create(request: UserRequest): User {
        val model = request.asModel()
        model.uPassword = passwordEncoder.encode(request.password)
        return repository.save(model)
    }

    override fun loadUserByUsername(email: String): UserDetails? {
        val res = repository.findByEmail(email)
        return if (!res.isEmpty) res.get()
        else null
    }

    override fun giveRole(id: Long, roleRequest: ChangeRoleRequest): User {
        val user: User = repository.findById(id).orElseThrow {
            logger.info("User nor found")
            ResourceNotFoundException("User")
        }
        user.takeIf { !it.containsRole(it, roleRequest) }?.roles?.add(UserRole(roleRequest.name))
        return repository.save(user)
    }

    override fun deleteRole(id: Long, roleRequest: ChangeRoleRequest): User {
        val user: User = repository.findById(id).orElseThrow {
            logger.info("User nor found")
            ResourceNotFoundException("User")
        }
        if (roleRequest.name != UserRoleType.USER)
            user.removeRole(user, roleRequest)
        else {
            logger.info("Bad credentials: there is no such role")
            throw BadCredentialsException()
        }
        return repository.save(user)
    }

    private fun findByEmail(email: String) =
        repository.findByEmail(email).orElseThrow {
            logger.info("User nor found")
            ResourceNotFoundException("User")
        }

    override fun update(resetPasswordRequest: ResetPasswordRequest): User {
        resetPasswordRequest.newPassword = passwordEncoder.encode(resetPasswordRequest.newPassword)
        var user = findByEmail(resetPasswordRequest.email)
        user.uPassword = resetPasswordRequest.newPassword
        logger.info("User: ${user.id} info updated")
        return repository.save(user)
    }


    override fun creditPercent(labPrice: Int, user: User): User {
        val percent = propertiesService.getPercent().property.toFloat()
        user.balance += (percent / 100) * labPrice
        logger.info("referral percent credited to user: ${user.id}")
        return repository.save(user)
    }

    override fun calculatePercent(labPrice: Int): Float {
        val percent = propertiesService.getPercent().property.toFloat()
        return (percent / 100) * labPrice
    }

    override fun getInvitedUsers(id: Long): MutableSet<InvitedUserResponse> {
        val res: MutableSet<InvitedUserResponse> = mutableSetOf()
        val user = findById(id)
        user.invitedUsers!!.forEach {
            res.add(InvitedUserResponse(it, it.referralDeductions))
        }
        return res
    }

    override fun canViewInvitedUsers(id: Long): Boolean {
        val user = meService.getMeInfo()
        return user.id == id
    }

    fun userHavePhoto(id: Long): Boolean {
        val user = findById(id)
        return user.photo != null
    }

    override fun findUserPhoto(id: Long): File {
        if (!userHavePhoto(id)) {
            logger.info("User photo not found")
            throw ResourceNotFoundException("Photo")
        }

        val photoId = findById(id).photo!!.id
        val filename: String = findById(id).photo!!.filename!!
        val filenameBd = userPhotoRepository.findById(photoId)
            .orElseThrow {
                logger.info("File not found")
                ResourceNotFoundException("File")
            }
            .filename

        if (filenameBd == null) {
            logger.error("UserPhoto.photoPath in is null")
            throw InternalServerErrorException()
        }

        if (filenameBd != filename) {
            logger.error("filenameBd != filename in uri: $filenameBd != $filename")
            throw ResourceNotFoundException("File")
        }

        val file = File(uploadsFolderPath.toString(), filename)
        if (!file.exists()) {
            logger.error("${file.absolutePath} not exist while database store it")
            throw InternalServerErrorException()
        }

        return file
    }

}