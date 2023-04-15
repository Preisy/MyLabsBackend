package ru.mylabs.mylabsbackend.controller

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.util.FileCopyUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.mylabs.mylabsbackend.model.dto.message.DeletedMessage
import ru.mylabs.mylabsbackend.model.dto.response.ApiResponse
import ru.mylabs.mylabsbackend.model.entity.TaskFile
import ru.mylabs.mylabsbackend.model.entity.UserPhoto
import ru.mylabs.mylabsbackend.service.userPhoto.UserPhotoService
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.InputStream
import java.net.URLConnection

@RestController
@RequestMapping("/me/photo")
class UserPhotoController(
    val userPhotoService: UserPhotoService
) {
    @PostMapping
    fun uploadPhoto(@RequestParam("file") file: MultipartFile): UserPhoto {
        return userPhotoService.uploadPhoto(file)
    }

    @DeleteMapping
    fun deletePhoto(): ResponseEntity<ApiResponse> {
        userPhotoService.deletePhoto()
        return DeletedMessage().asResponse()
    }

    @GetMapping
    fun getPhoto(response: HttpServletResponse) {
        val photo = userPhotoService.findUserPhoto()
        val mimeType = URLConnection.guessContentTypeFromName(photo.name)
        response.contentType = mimeType
        response.setHeader("Content-Disposition", "inline; filename=\"${photo.name}\"")
        response.setContentLength(photo.length().toInt())
        val inputStream: InputStream = BufferedInputStream(FileInputStream(photo))
        FileCopyUtils.copy(inputStream, response.outputStream)
    }
}