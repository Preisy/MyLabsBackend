package ru.mylabs.mylabsbackend.model.entity.labs

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIdentityReference
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.beans.factory.annotation.Value
import ru.mylabs.mylabsbackend.model.entity.AbstractEntity
import ru.mylabs.mylabsbackend.model.entity.User

@Entity

class UserLab(

    @Column(nullable = false, length = 255)
    var title: String,


    @Column
    var price: Int,

    @Column
    var type: String

    ) : AbstractEntity() {
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false, name = "user_id")
    lateinit var user: User
}
