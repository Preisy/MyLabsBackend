package ru.mylabs.mylabsbackend.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.mylabs.mylabsbackend.model.dto.message.DeletedMessage
import ru.mylabs.mylabsbackend.model.dto.request.OrderRequest
import ru.mylabs.mylabsbackend.model.dto.request.OrderStatusRequest
import ru.mylabs.mylabsbackend.model.dto.response.ApiResponse
import ru.mylabs.mylabsbackend.service.orderService.OrderService

@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderService: OrderService
) {
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    fun getAll(
        @RequestParam offset: Int?,
        @RequestParam limit: Int?
    ) = orderService.findAll(offset, limit)

    @PostMapping
    fun create(
        @RequestBody orderRequest: OrderRequest
    ) = orderService.create(orderRequest)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody orderRequest: OrderRequest
    ) = orderService.update(id, orderRequest)
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    fun patch(
        @PathVariable id: Long,
        @RequestBody orderRequest: OrderRequest
    ) = orderService.patch(id, orderRequest)

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/status")
    fun setStatus(
        @PathVariable id: Long,
        @RequestBody orderStatusRequest: OrderStatusRequest
    ) = orderService.setOrderStatus(id, orderStatusRequest)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<ApiResponse> {
        orderService.delete(id)
        return DeletedMessage().asResponse()
    }
}