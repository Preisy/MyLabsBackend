package ru.mylabs.mylabsbackend.utils.dateValidator

internal interface DateValidator {
    fun isValid(dateStr: String?): Boolean
}