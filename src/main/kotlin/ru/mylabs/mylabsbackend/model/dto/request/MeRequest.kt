package ru.mylabs.mylabsbackend.model.dto.request

import java.beans.ConstructorProperties

data class MeRequest
@ConstructorProperties("name", "contact")
constructor(var name: String, var contact: String)