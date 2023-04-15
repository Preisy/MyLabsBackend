package ru.mylabs.mylabsbackend.model.entity

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIdentityReference
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import jakarta.persistence.*
import ru.mylabs.mylabsbackend.model.entity.labs.LabType

@Entity
@Table(name = "order_table")
@JsonInclude(JsonInclude.Include.NON_NULL)
class Order(
    @Column(nullable = false)
    var deadline: String,
    @Column(nullable = true, length = 65534)
    var taskText: String?,
    @Column(nullable = true, length = 255)
    var executor: String? = "None",
    @Column(nullable = false)
    var type: LabType,
    @Column(nullable = true, length = 255)
    var promo: String? = ""
) : AbstractEntity() {

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    lateinit var user: User
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    val taskFiles: MutableList<TaskFile> = mutableListOf()
}