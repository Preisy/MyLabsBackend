package ru.mylabs.mylabsbackend.model.dto.request

import ru.mylabs.mylabsbackend.model.entity.Order

// todo bullshit
class OrderRequest(
    var username: String?,
    var contacts: String?,
    var deadline: String?,
    var taskText: String?,
    var executor: String?
) {
    fun asModel(): Order {
        if (username == null || contacts == null || deadline == null)
            throw NullPointerException()
        return Order(username!!, contacts!!, deadline!!, taskText, executor)
    }
}