package ru.mylabs.mylabsbackend.model.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mylabs.mylabsbackend.model.entity.UserPhoto

@Repository
interface UserPhotoRepository: CrudRepository<UserPhoto, Long>