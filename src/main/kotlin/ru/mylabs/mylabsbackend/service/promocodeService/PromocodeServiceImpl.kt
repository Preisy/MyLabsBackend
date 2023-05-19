package ru.mylabs.mylabsbackend.service.promocodeService

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.mylabs.mylabsbackend.model.dto.exception.ResourceNotFoundException
import ru.mylabs.mylabsbackend.model.entity.Promocode
import ru.mylabs.mylabsbackend.model.repository.PromocodeRepository

@Service
class PromocodeServiceImpl(
    private val promocodeRepository: PromocodeRepository
) : PromocodeService {
    private val logger = LoggerFactory.getLogger(PromocodeServiceImpl::class.java)
    override fun create(promoName: String): Promocode {
        val promocode = Promocode(promoName)
        logger.info("promocode: $promoName created")
        return promocodeRepository.save(promocode)
    }

    override fun delete(id: Long) {
        val promocode = promocodeRepository.findById(id).orElseThrow{ResourceNotFoundException("Promocode")}
        promocodeRepository.delete(promocode)
    }

    override fun findAll(): Iterable<Promocode> {
        return promocodeRepository.findAll()
    }
}