package ru.mylabs.mylabsbackend.utils.validators

import java.util.regex.Pattern

class EmailValidator(private val format: String) {
    fun patternMatches(emailAddress: String): Boolean {
        return Pattern.compile(this.format)
            .matcher(emailAddress)
            .matches()
    }
}