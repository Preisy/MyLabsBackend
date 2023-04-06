package ru.mylabs.mylabsbackend.model.entity.labs

import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.springframework.beans.factory.annotation.Value
import ru.mylabs.mylabsbackend.model.entity.AbstractEntity

@Entity
class Lab(
    @Column(nullable = false, length = 255)
    var title: String,

    @Column
    var duration: Int,

    @Column
    var price: Int,

    @Column
    var type: LabType,

    @Column
    @Value("0")
    var priority: Int
) : AbstractEntity()