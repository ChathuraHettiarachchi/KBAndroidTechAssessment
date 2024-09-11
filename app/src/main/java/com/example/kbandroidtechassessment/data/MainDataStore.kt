package com.example.kbandroidtechassessment.data

import android.provider.Settings.System.DATE_FORMAT
import com.example.kbandroidtechassessment.models.Transaction
import java.text.SimpleDateFormat
import java.util.Locale

internal fun getTransactions() = listOf(
    Transaction("2024-09-22", "Restaurant", -35.00),
    Transaction("2024-09-24", "Car Repair", -150.00),
    Transaction("2024-09-11", "Utilities", -150.00),
    Transaction("2024-09-19", "Clothing Store", -100.00),
    Transaction("2024-09-12", "Car Repair", -200.00),
    Transaction("2024-09-13", "Book Purchase", -30.00),
    Transaction("2024-09-14", "Electronics", -500.00),
    Transaction("2024-09-17", "Groceries", -70.00),
    Transaction("2024-09-26", "Electronics", -300.00),
    Transaction("2024-09-18", "Gym Membership", -50.00),
    Transaction("2024-09-01", "Coffee Shop", -15.00),
    Transaction("2024-09-02", "Grocery Store", -75.00),
    Transaction("2024-09-05", "Clothing Store", -120.00),
    Transaction("2024-09-06", "Gym Membership", -50.00),
    Transaction("2024-09-30", "Gym Membership", -50.00),
    Transaction("2024-09-15", "Vacation", -1500.00),
    Transaction("2024-09-07", "Movie Tickets", -30.00),
    Transaction("2024-09-08", "Salary", 2500.00),
    Transaction("2024-09-09", "Groceries", -80.00),
    Transaction("2024-09-23", "Groceries", -90.00),
    Transaction("2024-09-10", "Rent", -1200.00),
    Transaction("2024-09-20", "Movie Tickets", -25.00),
    Transaction("2024-09-21", "Gas Station", -55.00),
    Transaction("2024-09-25", "Utilities", -120.00),
    Transaction("2024-09-27", "Vacation", -1000.00),
    Transaction("2024-09-28", "Restaurant", -45.00),
    Transaction("2024-09-29", "Groceries", -85.00),
    Transaction("2024-09-16", "Restaurant", -40.00),
    Transaction("2024-09-03", "Restaurant", -35.00),
    Transaction("2024-09-04", "Gas Station", -60.00),
)