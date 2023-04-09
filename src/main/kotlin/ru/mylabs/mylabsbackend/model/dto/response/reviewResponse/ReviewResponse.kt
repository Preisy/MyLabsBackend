package ru.mylabs.mylabsbackend.model.dto.response.reviewResponse

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.beans.ConstructorProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ReviewResponse
@ConstructorProperties("response")
constructor(val response: Response)


