package ru.mylabs.mylabsbackend.service.userPhoto

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.mylabs.mylabsbackend.model.dto.exception.*
import ru.mylabs.mylabsbackend.model.entity.UserPhoto
import ru.mylabs.mylabsbackend.model.repository.UserPhotoRepository
import ru.mylabs.mylabsbackend.model.repository.UserRepository
import ru.mylabs.mylabsbackend.service.meService.MeService
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
class UserPhotoServiceImpl(
    private val userPhotoRepository: UserPhotoRepository,
    private val meService: MeService,
    private val userRepository: UserRepository
) : UserPhotoService {
    private val uploadsFolderPath = File("src/main/resources/uploads")
    private val logger = LoggerFactory.getLogger(UserPhotoServiceImpl::class.java)


    fun userHavePhoto(): Boolean {
        val user = meService.getMeInfo()
        return user.photo != null
    }

    override fun findUserPhoto(): File {
        if (!userHavePhoto())
            throw ResourceNotFoundException("Photo")

        val photoId = meService.getMeInfo().photo!!.id
        val filename: String = meService.getMeInfo().photo!!.filename!!
        val filenameBd = userPhotoRepository.findById(photoId)
            .orElseThrow { ResourceNotFoundException("File") }
            .filename

        if (filenameBd == null) {
            logger.error("UserPhoto.photoPath in is null")
            throw InternalServerErrorException()
        }

        if (filenameBd != filename) {
//            logger.error("filenameBd != filename in uri: $filenameBd != $filename")
            throw ResourceNotFoundException("File")
        }

        val file = File(uploadsFolderPath.toString(), filename)
        if (!file.exists()) {
            logger.error("${file.absolutePath} not exist while database store it")
            throw InternalServerErrorException()
        }

        return file
    }

    override fun deletePhotoFromStorage(): Boolean {
        val filename: String = meService.getMeInfo().photo!!.filename!!
        val file = File(uploadsFolderPath, filename)
        if (!file.exists()) return false
        file.delete()
        return true
    }

    override fun deletePhoto() {
        val photoId = meService.getMeInfo().photo!!.id
        val userPhoto = userPhotoRepository.findById(photoId)
            .orElseThrow { ResourceNotFoundException("File") }


        if (!userHavePhoto()) {
            throw ResourceNotFoundException("File")
        }
        val user = meService.getMeInfo()
        userPhotoRepository.deleteById(photoId)
        if (userPhoto.filename == null) throw InternalServerErrorException()

        if (!deletePhotoFromStorage()) {
            logger.error("${userPhoto.filename} not exist while database store it")
            throw InternalServerErrorException()
        }
        user.photo = null
        userRepository.save(user)
    }

    fun getFileExtension(filename: String): String {
        return filename.split('.').last()
    }

    fun writeFile(newFileName: String, multipartFile: MultipartFile) {
        val path: Path = Paths.get(uploadsFolderPath.toString(), newFileName)
        Files.newOutputStream(path).use { os ->
            val b = multipartFile.bytes
            os.write(b)
        }
    }

    override fun uploadPhoto(file: MultipartFile): UserPhoto {
        if (file.isEmpty) throw FileIsEmptyException()
        if (file.originalFilename == null) throw IncorrectFileName()
        if (file.originalFilename!!.split(".").last() != "png" && file.originalFilename!!.split(".").last() != "jpg")
            throw BadRequestException()
        if (file.size > 300 * 1024) throw FileIsTooBigException()
        uploadsFolderPath.mkdirs()
        var user = meService.getMeInfo()
        if (userHavePhoto()) {
            deletePhoto()
        }
        val userPhoto = userPhotoRepository.save(UserPhoto(user))
        user.photo = userPhoto
        userRepository.save(user)

        val newFileName = "${userPhoto.id}.${getFileExtension(file.originalFilename!!)}"
        writeFile(newFileName, file)

        userPhoto.filename = newFileName
        return userPhotoRepository.save(userPhoto)
    }

}