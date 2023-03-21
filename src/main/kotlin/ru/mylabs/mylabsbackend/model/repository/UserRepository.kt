package ru.mylabs.mylabsbackend.model.repository

import org.springframework.data.repository.CrudRepository
import ru.mylabs.mylabsbackend.model.entity.User

interface UserRepository : CrudRepository<User, Long> {
//    fun findUserByUsername(username: String): User
}