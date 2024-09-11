package com.example.kbandroidtechassessment.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long?.convertMillisToDate(): String {
    val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    return if (this != null && this > 0) {
        try {
            formatter.format(Date(this))
        } catch (e: Exception) {
            "Invalid date"
        }
    } else {
        "Invalid date"
    }
}