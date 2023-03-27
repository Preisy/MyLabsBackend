package ru.mylabs.mylabsbackend.controller

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.util.FileCopyUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.mylabs.mylabsbackend.model.dto.response.ApiResponse
import ru.mylabs.mylabsbackend.model.dto.message.DeletedMessage
import ru.mylabs.mylabsbackend.model.entity.TaskFile
import ru.mylabs.mylabsbackend.service.taskFileService.TaskFileService
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.InputStream
import java.net.URLConnection


@RestController
@RequestMapping("/orders/{orderId}/files")
class OrderFilesController(
    val taskFileService: TaskFileService
) {
    @GetMapping
    fun getAllFiles(@PathVariable orderId: Long): Iterable<TaskFile> {
        return taskFileService.findAllFilesInOrder(orderId)
    }

    @PostMapping
    fun uploadFile(@PathVariable orderId: Long, @RequestParam("file") file: MultipartFile): TaskFile {
        return taskFileService.uploadFile(orderId, file)
    }
    @DeleteMapping("/{fileId}")
    fun deleteFile(@PathVariable orderId: Long, @PathVariable fileId: Long): ResponseEntity<ApiResponse> {
        taskFileService.deleteFile(orderId, fileId)
        return DeletedMessage().asResponse()
    }

    @GetMapping("/{filename:.+}")
    fun getFile(response: HttpServletResponse,@PathVariable orderId: Long, @PathVariable filename: String) {
        val file = taskFileService.findFileById(orderId, filename)
        val mimeType = URLConnection.guessContentTypeFromName(file.name)
        response.contentType = mimeType
        response.setHeader("Content-Disposition", "inline; filename=\"${file.name}\"")
        response.setContentLength(file.length().toInt())
        val inputStream: InputStream = BufferedInputStream(FileInputStream(file))
        FileCopyUtils.copy(inputStream, response.outputStream)
    }
}