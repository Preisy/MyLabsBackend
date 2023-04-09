package ru.mylabs.mylabsbackend.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.mylabs.mylabsbackend.model.dto.message.DeletedMessage
import ru.mylabs.mylabsbackend.model.dto.request.UserLabRequest
import ru.mylabs.mylabsbackend.model.dto.response.ApiResponse
import ru.mylabs.mylabsbackend.model.entity.labs.UserLab
import ru.mylabs.mylabsbackend.service.userLabService.UserLabService

@RestController
@RequestMapping("/users/labs")
class UserLabController {
    @Autowired
    private lateinit var userLabService: UserLabService

    @GetMapping
    fun findAll(
        @RequestParam offset: Int?,
        @RequestParam limit: Int?,
    ): Iterable<UserLab> {
        return userLabService.findByUserId(offset, limit)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): UserLab {
        return userLabService.findById(id)
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun post(@RequestBody userLabRequest: UserLabRequest): UserLab {
        return userLabService.create(userLabRequest)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody userLabRequest: UserLabRequest): UserLab {
        return userLabService.update(id, userLabRequest)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<ApiResponse> {
        userLabService.delete(id)
        return DeletedMessage().asResponse()
    }

}