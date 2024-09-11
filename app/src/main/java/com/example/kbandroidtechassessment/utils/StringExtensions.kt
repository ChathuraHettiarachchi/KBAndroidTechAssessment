package com.example.kbandroidtechassessment.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.parseDate(dateFormat: SimpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())): Date {
    return dateFormat.parse(this) ?: throw IllegalArgumentException("Invalid date format")
}