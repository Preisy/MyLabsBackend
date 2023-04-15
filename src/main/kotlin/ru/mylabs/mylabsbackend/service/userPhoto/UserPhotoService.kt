package ru.mylabs.mylabsbackend.service.userPhoto

import org.springframework.web.multipart.MultipartFile
import ru.mylabs.mylabsbackend.model.entity.UserPhoto
import java.io.File

interface UserPhotoService {
    fun findUserPhoto(): File
    fun uploadPhoto(file: MultipartFile): UserPhoto
    fun deletePhoto()
    fun deletePhotoFromStorage(): Boolean
}