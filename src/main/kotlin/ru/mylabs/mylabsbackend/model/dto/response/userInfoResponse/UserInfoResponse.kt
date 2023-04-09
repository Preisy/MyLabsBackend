package ru.mylabs.mylabsbackend.model.dto.response.userInfoResponse

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.beans.ConstructorProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserInfoResponse
@ConstructorProperties("response")
constructor(val response: List<UserResponse>)

