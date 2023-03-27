package ru.mylabs.mylabsbackend.model.dto.request

import ru.mylabs.mylabsbackend.model.entity.AbstractEntity

interface ApiRequest {
    fun asModel(): AbstractEntity
}