package ru.mylabs.mylabsbackend.controller

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.util.FileCopyUtils
import org.springframework.web.bind.annotation.*
import ru.mylabs.mylabsbackend.model.dto.message.DeletedMessage
import ru.mylabs.mylabsbackend.model.dto.request.ChangeRoleRequest
import ru.mylabs.mylabsbackend.model.dto.request.UserRequest
import ru.mylabs.mylabsbackend.model.dto.response.ApiResponse
import ru.mylabs.mylabsbackend.service.userService.UserService
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.InputStream
import java.net.URLConnection


@RestController
@RequestMapping("/users")
class UsersController(
    private val userService: UserService
) {
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    fun getAll() = userService.findAll()
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun create(@RequestBody userRequest: UserRequest) = userService.create(userRequest)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<ApiResponse> {
        userService.delete(id)
        return DeletedMessage().asResponse()
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = userService.findById(id)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/roles")
    fun giveRole(@PathVariable id: Long, @RequestBody roleRequest: ChangeRoleRequest) =
        userService.giveRole(id, roleRequest)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/roles")
    fun deleteRole(@PathVariable id: Long, @RequestBody roleRequest: ChangeRoleRequest) =
        userService.deleteRole(id, roleRequest)
    @PreAuthorize("hasRole('ADMIN')|| @UserService.canViewInvitedUsers(#id)")
    @GetMapping("/{id}/invited")
    fun getInvitedUsers(@PathVariable id: Long) = userService.getInvitedUsers(id)
    @GetMapping("/{id}/photo")
    fun getPhoto(@PathVariable id: Long, response: HttpServletResponse) {
        val photo = userService.findUserPhoto(id)
        val mimeType = URLConnection.guessContentTypeFromName(photo.name)
        response.contentType = mimeType
        response.setHeader("Content-Disposition", "inline; filename=\"${photo.name}\"")
        response.setContentLength(photo.length().toInt())
        val inputStream: InputStream = BufferedInputStream(FileInputStream(photo))
        FileCopyUtils.copy(inputStream, response.outputStream)
    }
}