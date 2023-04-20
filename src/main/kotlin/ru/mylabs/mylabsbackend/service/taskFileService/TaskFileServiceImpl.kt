package ru.mylabs.mylabsbackend.service.taskFileService

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.mylabs.mylabsbackend.model.dto.exception.*
import ru.mylabs.mylabsbackend.model.entity.Order
import ru.mylabs.mylabsbackend.model.entity.TaskFile
import ru.mylabs.mylabsbackend.model.repository.OrderRepository
import ru.mylabs.mylabsbackend.model.repository.TaskFileRepository
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
class TaskFileServiceImpl(
    private val orderRepository: OrderRepository,
    private val taskFileRepository: TaskFileRepository
) : TaskFileService {
    private val uploadsFolderPath = File("src/main/resources/uploads")
    private val logger = LoggerFactory.getLogger(TaskFileServiceImpl::class.java)

    override fun findAllFilesInOrder(orderId: Long): Iterable<TaskFile> {
        val order = orderRepository
            .findById(orderId)
            .orElseThrow { ResourceNotFoundException("Order") }
        return order.taskFiles
    }

    override fun findFileById(orderId: Long, filename: String): File {
        if (!filename.contains(".")) throw IncorrectFileName()
        val fileId = filename.split(".").first()
            .toLongOrNull() ?: throw IncorrectFileName()

        val filenameBd = taskFileRepository.findById(fileId)
            .orElseThrow { ResourceNotFoundException("File") }
            .filename

        if (filenameBd == null) {
            logger.error("TaskFile.taskPath in is null")
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

    override fun deleteFileFromStorage(filename: String): Boolean {
        val file = File(uploadsFolderPath, filename)
        if (!file.exists()) return false
        file.delete()
        return true
    }

    override fun deleteFile(orderId: Long, fileId: Long) {
        val taskFile = taskFileRepository.findById(fileId)
            .orElseThrow { ResourceNotFoundException("File") }

        val isOrderContainsFile = orderRepository.findById(orderId)
            .orElseThrow { ResourceNotFoundException("Order") }
            .taskFiles.contains(taskFile)
        if (!isOrderContainsFile) {
            throw ResourceNotFoundException("File")
        }

        taskFileRepository.deleteById(fileId)
        if (taskFile.filename == null) throw InternalServerErrorException()

        if (!deleteFileFromStorage(taskFile.filename!!)) {
            logger.error("${taskFile.filename} not exist while database store it")
            throw InternalServerErrorException()
        }
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

    override fun uploadFile(orderId: Long, file: MultipartFile): TaskFile {
        if (file.isEmpty) throw FileIsEmptyException()
        if (file.originalFilename == null) throw IncorrectFileName()
        if (file.size > 10 * 1024 * 1024) throw FileIsTooBigException()
        uploadsFolderPath.mkdirs()

        val order: Order = orderRepository.findById(orderId).orElseThrow { ResourceNotFoundException("Order") }
        val taskFile = taskFileRepository.save(TaskFile(order))

        val newFileName = "${taskFile.id}.${getFileExtension(file.originalFilename!!)}"
        writeFile(newFileName, file)

        taskFile.filename = newFileName
        return taskFileRepository.save(taskFile)
    }

}