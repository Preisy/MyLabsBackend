package ru.mylabs.mylabsbackend.service.userLabService

import org.springframework.data.domain.Sort
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
    override fun create(userLabRequest: UserLabRequest): UserLab {
        val model = userLabRequest.asModel()
        model.user = meService.getMeInfo()
        return userLabRepository.save(model)
    }

    override fun findByUserId(offset: Int?, limit: Int?): Iterable<UserLab> {
        if (userLabRepository.count() <= (offset ?: 0) + (limit ?: 0)) return emptyList()
        val user = meService.getMeInfo()
        var res = userLabRepository.findByUserId(Sort.by(Sort.Direction.DESC, "priority"), user.id).drop(offset ?: 0)
        if (limit != null) res = res.dropLast(res.size - limit)
        return res
    }

    override fun findById(id: Long): UserLab {
        val user = meService.getMeInfo()
        val res = userLabRepository.findById(id).orElseThrow { ResourceNotFoundException("Lab not found") }
        if (res.user.id != user.id) throw ResourceNotFoundException("Lab not found")
        return res
    }

    override fun update(id: Long, userLabRequest: UserLabRequest) = findById(id).apply {
        title = userLabRequest.title
        duration = userLabRequest.duration
        price = userLabRequest.price
        type = userLabRequest.type
        priority = userLabRequest.priority
        userLabRepository.save(this)
    }

    override fun delete(id: Long) {
        if (!userLabRepository.existsById(id)) throw ResourceNotFoundException("Lab not found")
        userLabRepository.deleteById(id)
    }

}

