package ru.mylabs.mylabsbackend.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.mylabs.mylabsbackend.model.dto.message.DeletedMessage
import ru.mylabs.mylabsbackend.model.dto.request.UserRequest
import ru.mylabs.mylabsbackend.model.dto.response.ApiResponse
import ru.mylabs.mylabsbackend.service.userService.UserService


@RestController
@RequestMapping("/users")
class UsersController(
    private val userService: UserService
) {
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
    @PatchMapping("/roles/{id}")
    fun giveRole(@PathVariable id: Long) = userService.giveRole(id)

}