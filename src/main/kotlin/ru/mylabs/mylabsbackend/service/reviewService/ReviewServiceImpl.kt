package ru.mylabs.mylabsbackend.service.reviewService

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.mylabs.mylabsbackend.model.dto.response.reviewResponse.Items
import ru.mylabs.mylabsbackend.model.dto.response.reviewResponse.ReviewResponse
import ru.mylabs.mylabsbackend.model.dto.response.userInfoResponse.UserInfoResponse
import ru.mylabs.mylabsbackend.model.dto.response.userInfoResponse.UserResponse


@Service
class ReviewServiceImpl : ReviewService {
    private val baseUrl = "https://api.vk.com/method"
    private val token =
        "vk1.a.FB2W5HuE4YsExR4h0D6DX90q2hpiLdUKP6_ORnjSAjT-XFK8MYUMGuUjxHSpA-lYycxUn84zBN8psvOBvi_mgGb4C4mLIaaMIXXiUpOJsf0Sj3rlbgfTJiF5XXeCW-U1PctGa5dmddB3bLSnQtzXzm3Dte7EkUoW3uI2vEmalLP7v3oStAEi3IB32_vBUdk7oLzhREueA_yqH_U21hRUMA"
    private val version = "5.131"

    private fun <T> request(url: String, clazz: Class<T>): T {
        val restTemplate = RestTemplate()
        val str = restTemplate.getForObject(url, String::class.java)
        return ObjectMapper().readValue(str, clazz)
    }

    override fun getReviews(): Iterable<Items> {
        val url = "$baseUrl/wall.getComments?owner_id=270022066&post_id=765&count=100&access_token=$token&v=$version"
        val res = request(url, ReviewResponse::class.java)
        val builder = StringBuilder()
        res.response.items.forEach {
            builder.append(",${it.fromId}")
        }
        val userInfo = getUserInfo(builder.toString())
        val map: MutableMap<Long?, UserResponse> = mutableMapOf()

        userInfo?.response?.forEach {
            map[it.id] = it
        }
        res.response.items.forEach {
            it.commentUrl = "https://vk.com/id270022066?w=wall270022066_765_r${it.id}"
            val userProps = map.getValue(it.fromId)
            it.name = userProps.name
            it.surname = userProps.surname
            it.photo = userProps.photo
        }
        return res.response.items
    }

    override fun getUserInfo(ids: String?): UserInfoResponse? {
        val url = "$baseUrl/users.get?user_ids=$ids&fields=photo_200&access_token=$token&v=$version"
        return request(url, UserInfoResponse::class.java)
    }
}