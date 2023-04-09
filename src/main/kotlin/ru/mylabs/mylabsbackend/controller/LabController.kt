package ru.mylabs.mylabsbackend.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.mylabs.mylabsbackend.model.dto.message.DeletedMessage
import ru.mylabs.mylabsbackend.model.dto.request.LabRequest
import ru.mylabs.mylabsbackend.model.dto.request.LabsQuantityRequest
import ru.mylabs.mylabsbackend.model.dto.response.ApiResponse
import ru.mylabs.mylabsbackend.model.entity.labs.Lab
import ru.mylabs.mylabsbackend.model.entity.labs.LabsQuantity
import ru.mylabs.mylabsbackend.service.labService.LabService

@RestController
@RequestMapping("/labs")
class LabController {
    @Autowired
    private lateinit var labService: LabService

    @GetMapping
    fun findAll(
        @RequestParam offset: Int?,
        @RequestParam limit: Int?,
    ): Iterable<Lab> {
        return labService.findAll(offset, limit)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): Lab {
        return labService.findById(id)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun post(@RequestBody labRequest: LabRequest): Lab {
        return labService.create(labRequest)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody labRequest: LabRequest): Lab {
        return labService.update(id, labRequest)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<ApiResponse> {
        labService.delete(id)
        return DeletedMessage().asResponse()
    }


    @GetMapping("/quantity")
    fun getQuantity(): LabsQuantity {
        return labService.getQuantity()
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/quantity")
    fun setQuantity(@RequestBody labsQuantityRequest: LabsQuantityRequest): LabsQuantity {
        return labService.setQuantity(labsQuantityRequest)
    }
}