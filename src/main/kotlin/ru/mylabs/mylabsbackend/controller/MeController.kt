package ru.mylabs.mylabsbackend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
    fun putMeInfo(request: MeRequest) = meService.putMeInfo(request)
}