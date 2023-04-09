package ru.mylabs.mylabsbackend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.mylabs.mylabsbackend.service.reviewService.ReviewService

@RestController
class ReviewController(
    private val reviewService: ReviewService
) {
    @GetMapping
    @RequestMapping("/reviews")
    fun getReviews() = reviewService.getReviews()
}