package ru.mylabs.mylabsbackend.service.labService

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.mylabs.mylabsbackend.model.dto.exception.ResourceNotFoundException
import ru.mylabs.mylabsbackend.model.dto.request.LabRequest
import ru.mylabs.mylabsbackend.model.entity.labs.Lab
import ru.mylabs.mylabsbackend.model.repository.LabRepository

@Service
class LabServiceImpl(
    val labRepository: LabRepository
) : LabService {
    private val logger = LoggerFactory.getLogger(LabServiceImpl::class.java)
    override fun create(labRequest: LabRequest): Lab {
        logger.info("Lab created (id: ${labRepository.save(labRequest.asModel()).id})")
        return labRepository.save(labRequest.asModel())
    }


    override fun findAll(offset: Int?, limit: Int?): Iterable<Lab> {
        if (labRepository.count() <= (offset ?: 0) + (limit ?: 0)) return emptyList()
        var res = labRepository.findAll(Sort.by(Sort.Direction.DESC, "priority")).drop(offset ?: 0)
        if (limit != null) res = res.dropLast(res.size - limit)
        return res
    }

    override fun findById(id: Long): Lab =
        labRepository.findById(id).orElseThrow {
            logger.info("Lab not found")
            ResourceNotFoundException("Lab")
        }

    override fun update(id: Long, labRequest: LabRequest) = findById(id).apply {
        title = labRequest.title
        duration = labRequest.duration
        price = labRequest.price
        type = labRequest.type
        priority = labRequest.priority
        labRepository.save(this)
    }

    override fun delete(id: Long) {
        if (!labRepository.existsById(id)) {
            logger.info("Lab not found")
            throw ResourceNotFoundException("Lab")
        }
        labRepository.deleteById(id)
    }

}

