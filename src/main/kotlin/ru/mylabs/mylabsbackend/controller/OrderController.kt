package ru.mylabs.mylabsbackend.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.mylabs.mylabsbackend.model.dto.message.DeletedMessage
import ru.mylabs.mylabsbackend.model.dto.request.OrderRequest
import ru.mylabs.mylabsbackend.model.dto.request.OrderStatusRequest
import ru.mylabs.mylabsbackend.model.dto.response.ApiResponse
import ru.mylabs.mylabsbackend.model.entity.Order
import ru.mylabs.mylabsbackend.service.meService.MeService
import ru.mylabs.mylabsbackend.service.orderService.OrderService

@RestController
class OrderController(
    private val orderService: OrderService,
    private val meService: MeService
) {
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders")
    fun getAll(
        @RequestParam offset: Int?,
        @RequestParam limit: Int?
    ) = orderService.findAll(offset, limit)

    @PostMapping("/orders")
    fun create(
        @RequestBody orderRequest: OrderRequest
    ) = orderService.create(orderRequest)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/orders/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody orderRequest: OrderRequest
    ) = orderService.update(id, orderRequest)
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/orders/{id}")
    fun patch(
        @PathVariable id: Long,
        @RequestBody orderRequest: OrderRequest
    ) = orderService.patch(id, orderRequest)

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/orders/{id}/status")
    fun setStatus(
        @PathVariable id: Long,
        @RequestBody orderStatusRequest: OrderStatusRequest
    ) = orderService.setOrderStatus(id, orderStatusRequest)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/orders/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<ApiResponse> {
        orderService.delete(id)
        return DeletedMessage().asResponse()
    }
    @GetMapping("/users/orders")
    fun findByAuthId(
        @RequestParam offset: Int?,
        @RequestParam limit: Int?,
    ): Iterable<Order> {
        val user = meService.getMeInfo()
        return orderService.findByUserId(user.id, offset, limit)
    }
    @GetMapping("/users/{id}/orders")
    @PreAuthorize("hasRole('ADMIN')")
    fun findByUserId(
        @PathVariable id: Long,
        @RequestParam offset: Int?,
        @RequestParam limit: Int?,
    ): Iterable<Order> {
        return orderService.findByUserId(id, offset, limit)
    }
}