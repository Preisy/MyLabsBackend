package ru.mylabs.mylabsbackend.model.dto.response.userInfoResponse

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.beans.ConstructorProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserResponse
@ConstructorProperties("id", "name", "surname", "photo")
constructor(
    val id: Long?,
    @JsonProperty("first_name")
    val name: String?,
    @JsonProperty("last_name")
    val surname: String?,
    @JsonProperty("photo_200")
    val photo: String?
)