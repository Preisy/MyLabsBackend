package ru.mylabs.mylabsbackend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.mylabs.mylabsbackend.model.dto.ApiResponse
import ru.mylabs.mylabsbackend.model.dto.message.DeletedMessage
import ru.mylabs.mylabsbackend.service.orderService.OrderService
import ru.mylabs.mylabsbackend.model.dto.request.OrderRequest
import ru.mylabs.mylabsbackend.model.dto.request.OrderStatusRequest

@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderService: OrderService
) {
    @GetMapping
    fun getAll(
        @RequestParam offset: Int?,
        @RequestParam limit: Int?
    ) = orderService.findAll(offset, limit)

    @PostMapping
    fun create(
        @RequestBody orderRequest: OrderRequest
    ) = orderService.create(orderRequest)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody orderRequest: OrderRequest
    ) = orderService.update(id, orderRequest)

    @PatchMapping("/{id}")
    fun patch(
        @PathVariable id: Long,
        @RequestBody orderRequest: OrderRequest
    ) = orderService.patch(id, orderRequest)

    @PostMapping("/{id}/status")
    fun setStatus(
        @PathVariable id: Long,
        @RequestBody orderStatusRequest: OrderStatusRequest
    ) = orderService.setOrderStatus(id, orderStatusRequest)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<ApiResponse> {
        orderService.delete(id)
        return DeletedMessage().asResponse()
    }
}