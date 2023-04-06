package ru.mylabs.mylabsbackend.service.labService

import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.mylabs.mylabsbackend.model.dto.exception.ResourceNotFoundException
import ru.mylabs.mylabsbackend.model.entity.labs.Lab
import ru.mylabs.mylabsbackend.model.entity.labs.LabsQuantity
import ru.mylabs.mylabsbackend.model.repository.LabQuantityRepository
import ru.mylabs.mylabsbackend.model.repository.LabRepository
import ru.mylabs.mylabsbackend.model.dto.request.LabRequest
import ru.mylabs.mylabsbackend.model.dto.request.LabsQuantityRequest

@Service
class LabServiceImpl(
    val labRepository: LabRepository,
    val labQuantityRepository: LabQuantityRepository
) : LabService {
    override fun create(labRequest: LabRequest) =
        labRepository.save(labRequest.asModel())

    override fun findAll(offset: Int?, limit: Int?): Iterable<Lab> {
        if (labRepository.count() <= (offset ?: 0) + (limit ?: 0)) return emptyList()
        var res = labRepository.findAll(Sort.by(Sort.Direction.DESC, "priority")).drop(offset ?: 0)
        if (limit != null) res = res.dropLast(res.size - limit)
        return res
    }

    override fun findById(id: Long): Lab =
        labRepository.findById(id).orElseThrow { ResourceNotFoundException("Lab not found") }

    override fun update(id: Long, labRequest: LabRequest) = findById(id).apply {
        title = labRequest.title
        duration = labRequest.duration
        price = labRequest.price
        type = labRequest.type
        priority = labRequest.priority
        labRepository.save(this)
    }

    override fun delete(id: Long) {
        if (!labRepository.existsById(id)) throw ResourceNotFoundException("Lab not found")
        labRepository.deleteById(id)
    }

    override fun getQuantity(): LabsQuantity {
        val res = labQuantityRepository.findById(1);
        if (res.isEmpty) {
            val quantity = LabsQuantity(labRepository.count())
            return labQuantityRepository.save(quantity)
        }
        return res.orElseThrow()
    }

    override fun setQuantity(quantity: LabsQuantityRequest): LabsQuantity {
        val res = labQuantityRepository.findById(1)
        return if (res.isEmpty) {
            val q = LabsQuantity(labRepository.count())
            labQuantityRepository.save(q)
        } else {
            res.orElseThrow().apply {
                this.quantity = quantity.quantity
                labQuantityRepository.save(this)
            }
        }
    }
}

