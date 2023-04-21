package ru.mylabs.mylabsbackend.utils.validators.dateValidator

internal interface DateValidator {
    fun isValid(dateStr: String?): Boolean
}