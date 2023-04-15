package ru.mylabs.mylabsbackend.controller

import org.springframework.web.bind.annotation.*
import ru.mylabs.mylabsbackend.model.dto.request.MeRequest
import ru.mylabs.mylabsbackend.model.dto.request.UserRequest
import ru.mylabs.mylabsbackend.service.meService.MeService

@RestController
@RequestMapping("/me")
class MeController(
    private val meService: MeService
) {
    @GetMapping
    fun getMeInfo() = meService.getMeInfo()

    @PutMapping
    fun putMeInfo(@RequestBody request: MeRequest) = meService.putMeInfo(request)
}