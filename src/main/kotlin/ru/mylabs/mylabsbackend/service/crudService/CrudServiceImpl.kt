package ru.mylabs.mylabsbackend.service.crudService

import org.springframework.data.repository.CrudRepository
import ru.mylabs.mylabsbackend.model.dto.exception.NotCorrespondingToModel
import ru.mylabs.mylabsbackend.model.dto.exception.ResourceNotFoundException
import ru.mylabs.mylabsbackend.model.dto.request.ApiRequest
import ru.mylabs.mylabsbackend.model.entity.AbstractEntity

abstract class CrudServiceImpl<
        Request : ApiRequest,
        Model : AbstractEntity,
        Id : Any,
        Repository : CrudRepository<Model, Id>>(
    protected val modelSimpleName: String? = null,
) : CrudService<Request, Model, Id> {

    protected abstract val repository: Repository

    override fun create(request: Request): Model {
        val model = request.asModel() as? Model ?: throw NotCorrespondingToModel()
        return repository.save(model)
    }

    override fun findAll(): Iterable<Model> = repository.findAll()
    override fun findById(id: Id): Model = repository.findById(id).orElseThrow {
        notFoundException()
    }

    override fun delete(id: Id) {
        if (!repository.existsById(id)) throw notFoundException()
        repository.deleteById(id)
    }

    private fun notFoundException(): ResourceNotFoundException {
        if (modelSimpleName == null) return ResourceNotFoundException()
        else return ResourceNotFoundException(modelSimpleName)
    }
}