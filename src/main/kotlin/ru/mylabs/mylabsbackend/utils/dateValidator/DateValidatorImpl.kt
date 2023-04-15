package ru.mylabs.mylabsbackend.utils.dateValidator

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat


class DateValidatorImpl(private val dateFormat: String) : DateValidator {

    override fun isValid(dateStr: String?): Boolean {
        val sdf: DateFormat = SimpleDateFormat(this.dateFormat)
        sdf.isLenient = false
        try {
            sdf.parse(dateStr)
        } catch (e: ParseException) {
            return false
        }
        return true
    }
}