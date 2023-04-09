package ru.mylabs.mylabsbackend.model.repository

import org.springframework.data.domain.Sort
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mylabs.mylabsbackend.model.entity.labs.UserLab

@Repository
interface UserLabRepository : CrudRepository<UserLab, Long> {
    fun findByUserId(sort: Sort, userId: Long): Iterable<UserLab>
}