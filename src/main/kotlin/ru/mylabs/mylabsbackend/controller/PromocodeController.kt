package ru.mylabs.mylabsbackend.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.mylabs.mylabsbackend.service.promocodeService.PromocodeService

@RestController
@RequestMapping("/promocodes")
@PreAuthorize("hasRole('ADMIN')")
class PromocodeController(
    private val promocodeService: PromocodeService
) {
    @PostMapping
    fun create(@RequestParam promoName: String) = promocodeService.create(promoName)
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id:Long) = promocodeService.delete(id)
    @GetMapping
    fun getAll() = promocodeService.findAll()
}