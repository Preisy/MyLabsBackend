package ru.mylabs.mylabsbackend.model.dto.response.reviewResponse

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.beans.ConstructorProperties

@JsonIgnoreProperties(ignoreUnknown = true)

data class Items
@ConstructorProperties("fromId", "text", "id", "commentUrl", "name", "surname", "photo")
constructor(
    @JsonProperty("from_id")
    val fromId: Long?,
    val text: String?,
    val id: Long?,
    var commentUrl: String?,
    @JsonProperty("first_name")
    var name: String?,
    @JsonProperty("last_name")
    var surname: String?,
    @JsonProperty("photo_200")
    var photo: String?
)