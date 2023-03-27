package ru.mylabs.mylabsbackend.model.dto.request

import java.beans.ConstructorProperties

data class LoginRequest
@ConstructorProperties("email", "password")
constructor(val email: String, val password: String)