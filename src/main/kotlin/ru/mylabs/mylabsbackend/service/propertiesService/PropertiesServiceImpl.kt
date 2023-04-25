package ru.mylabs.mylabsbackend.service.propertiesService

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.mylabs.mylabsbackend.model.dto.request.LabsQuantityRequest
import ru.mylabs.mylabsbackend.model.dto.request.ReferralPercentageRequest
import ru.mylabs.mylabsbackend.model.entity.Property
import ru.mylabs.mylabsbackend.model.repository.LabRepository
import ru.mylabs.mylabsbackend.model.repository.PropertiesRepository

@Service
class PropertiesServiceImpl(
    val labRepository: LabRepository,
    val propertiesRepository: PropertiesRepository
) : PropertiesService {
    private val logger = LoggerFactory.getLogger(PropertiesServiceImpl::class.java)
    private fun findById(id: String, callback: () -> String): Property {
        val res = propertiesRepository.findById(id)
        return if (res.isEmpty) {
            propertiesRepository.save(Property(id, callback()))
        } else res.get()
    }

    override fun getQuantity(): Property = findById("labs_quantity") { labRepository.count().toString() }

    override fun setQuantity(quantity: LabsQuantityRequest): Property {
        var res = getQuantity()
        res.property = quantity.quantity
        logger.info("lab's examples quantity set to ${quantity.quantity}")
        return propertiesRepository.save(res)
    }

    override fun getPercent(): Property = findById("referral_percentage") { "10" }

    override fun setPercent(newPercent: ReferralPercentageRequest): Property {
        var res = getPercent()
        res.property = newPercent.percent
        logger.info(" referral percent set to ${newPercent.percent}")
        return propertiesRepository.save(res)
    }
}