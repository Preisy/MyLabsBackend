package ru.mylabs.mylabsbackend.utils

import java.util.regex.Pattern

class EmailValidator(private val format: String) {
    fun patternMatches(emailAddress: String): Boolean {
        return Pattern.compile(this.format)
            .matcher(emailAddress)
            .matches()
    }
}