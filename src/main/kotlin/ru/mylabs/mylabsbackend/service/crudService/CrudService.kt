package ru.mylabs.mylabsbackend.service.crudService

import ru.mylabs.mylabsbackend.model.dto.request.ApiRequest
import ru.mylabs.mylabsbackend.model.entity.AbstractEntity


interface CrudService<Request : ApiRequest, Model : AbstractEntity, Id : Any> {

    fun create(request: Request): Model
    fun findAll(): Iterable<Model>
    fun findById(id: Id): Model

    fun delete(id: Id)

}