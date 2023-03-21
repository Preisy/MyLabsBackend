package ru.mylabs.mylabsbackend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    @GetMapping("/health")
    fun get(): String {
        return "Get Ok"
    }
    @PostMapping("/health")
    fun post(): String {
        return "Post Ok"
    }
}