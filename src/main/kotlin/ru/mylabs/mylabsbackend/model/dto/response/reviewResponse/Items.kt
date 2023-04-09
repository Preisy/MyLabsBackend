package ru.mylabs.mylabsbackend.model.dto.response.reviewResponse

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.beans.ConstructorProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Items
@ConstructorProperties("fromId", "text", "userUrl", "name", "surname", "photo")
constructor(
    @JsonProperty("from_id")
    val fromId: Long?,
    val text: String?,
    var userUrl: String?,
    @JsonProperty("first_name")
    var name: String?,
    @JsonProperty("second_name")
    var surname: String?,
    @JsonProperty("photo_200")
    var photo: String?
)