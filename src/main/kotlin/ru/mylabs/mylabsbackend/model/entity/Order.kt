package ru.mylabs.mylabsbackend.model.entity

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.*

@Entity
@Table(name = "order_table")
@JsonInclude(JsonInclude.Include.NON_NULL)
class Order(
    @Column(nullable = false, length = 255)
    var username: String,
    @Column(nullable = false, length = 255)
    var contacts: String,
    @Column(nullable = false)
    var deadline: String,
    @Column(nullable = true, length = 65534)
    var taskText: String?,
    @Column(nullable = true, length = 255)
    var executor: String? = "None"
) : AbstractEntity() {
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    val taskFiles: MutableList<TaskFile> = mutableListOf()
}