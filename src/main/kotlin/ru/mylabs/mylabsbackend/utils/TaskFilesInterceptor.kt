package ru.mylabs.mylabsbackend.utils

import org.hibernate.Interceptor
import org.hibernate.SessionFactory
import org.hibernate.type.Type
import org.springframework.stereotype.Component
import ru.mylabs.mylabsbackend.model.entity.TaskFile
import ru.mylabs.mylabsbackend.model.entity.UserPhoto
import ru.mylabs.mylabsbackend.service.taskFileService.TaskFileService
import ru.mylabs.mylabsbackend.service.userPhoto.UserPhotoService
import java.io.Serializable
@Component
class TaskFilesInterceptor(
   private val taskFileService: TaskFileService,
    private val userPhotoService: UserPhotoService,
  //  private val sessionFactory: SessionFactory

) : Interceptor, Serializable {
    override fun onDelete(
        entity: Any?,
        id: Any?,
        state: Array<Any?>?,
        propertyNames: Array<String?>?,
        types: Array<Type?>?
    ) {
        if (entity is TaskFile) {
            taskFileService.deleteFile(entity.order.id, entity.id)
        }
        if (entity is UserPhoto) {
            userPhotoService.deletePhoto()
        }
    }
}