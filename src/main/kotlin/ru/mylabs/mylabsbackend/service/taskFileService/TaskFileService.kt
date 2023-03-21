package ru.mylabs.mylabsbackend.service.taskFileService

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.mylabs.mylabsbackend.model.entity.TaskFile
import java.io.File

@Service
interface TaskFileService {
    fun findAllFilesInOrder(orderId: Long): Iterable<TaskFile>
    fun findFileById(orderId: Long, filename: String): File
    fun uploadFile(orderId: Long, file: MultipartFile): TaskFile
    fun deleteFile(orderId: Long, fileId: Long)
    fun deleteFileFromStorage(filename: String): Boolean
}