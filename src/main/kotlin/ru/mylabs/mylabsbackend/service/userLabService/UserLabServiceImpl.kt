package ru.mylabs.mylabsbackend.service.userLabService

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.mylabs.mylabsbackend.model.dto.exception.ResourceNotFoundException
import ru.mylabs.mylabsbackend.model.dto.request.UserLabRequest
import ru.mylabs.mylabsbackend.model.entity.labs.UserLab
import ru.mylabs.mylabsbackend.model.repository.UserLabRepository
import ru.mylabs.mylabsbackend.service.meService.MeService


@Service
class UserLabServiceImpl(
    val userLabRepository: UserLabRepository,
    val meService: MeService
) : UserLabService {
    private val logger = LoggerFactory.getLogger(UserLabServiceImpl::class.java)
    override fun create(userLabRequest: UserLabRequest): UserLab {
        val model = userLabRequest.asModel()
        model.user = meService.getMeInfo()
        val lab = userLabRepository.save(model)
        logger.info("Lab: ${lab.id} added to user: ${lab.user.id}")
        return lab
    }

    override fun findByUserId(id: Long, offset: Int?, limit: Int?): Iterable<UserLab> {
        if (userLabRepository.count() <= (offset ?: 0) + (limit ?: 0)) return emptyList()
        var res = userLabRepository.findByUserId(id).drop(offset ?: 0)
        if (limit != null) res = res.dropLast(res.size - limit)
        return res
    }

    override fun findById(id: Long): UserLab {
        val user = meService.getMeInfo()
        val res = userLabRepository.findById(id).orElseThrow {
            logger.info("Lab not found")
            ResourceNotFoundException("Lab")
        }
        if (res.user.id != user.id) {
            logger.info("Lab not found")
            throw ResourceNotFoundException("Lab")
        }
        return res
    }

    override fun update(id: Long, userLabRequest: UserLabRequest) = findById(id).apply {
        title = userLabRequest.title
        price = userLabRequest.price
        type = userLabRequest.type
        logger.info("Lab: ${this.id} info updated")
        userLabRepository.save(this)
    }

    override fun delete(id: Long) {
        if (!userLabRepository.existsById(id)) {
            logger.info("Lab not found")
            throw ResourceNotFoundException("Lab")
        }
        logger.info("Lab: $id deleted")
        userLabRepository.deleteById(id)
    }

}

