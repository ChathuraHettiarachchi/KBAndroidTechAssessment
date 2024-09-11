package com.example.kbandroidtechassessment.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kbandroidtechassessment.data.getTransactions
import com.example.kbandroidtechassessment.models.Transaction
import com.example.kbandroidtechassessment.utils.parseDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel : ViewModel() {
    private val _transactions = MutableStateFlow(getTransactions())
    private val _selectedDateRange = MutableStateFlow<Pair<String, String>?>(null)

    // region transaction related variables
    val selectedDateRange: StateFlow<Pair<String, String>?> = _selectedDateRange

    // compute filtered transactions based on date range
    val filteredTransactions: StateFlow<List<Transaction>> = combine(
        _transactions,
        _selectedDateRange
    ) { transactions, dateRange ->
        dateRange?.let { (startDate, endDate) ->
            try {
                val start = startDate.parseDate()
                val end = endDate.parseDate()
                transactions.filter {
                    val transactionDate = it.date.parseDate()
                    transactionDate in start..end
                }
            } catch (e: IllegalArgumentException) {
                emptyList()
            }
        } ?: transactions
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // compute available amount based on filtered transactions
    val availableAmount: StateFlow<Double> = filteredTransactions
        .map { transactionsList -> transactionsList.sumOf { it.amount } }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0.0)
    // endregion

    // region transaction actions
    fun setDateRange(startDate: String, endDate: String) {
        _selectedDateRange.value = Pair(startDate, endDate)
    }

    fun clearDateRange() {
        _selectedDateRange.value = null
    }
    // endregion
}