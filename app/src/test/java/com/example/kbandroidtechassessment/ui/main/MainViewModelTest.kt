package com.example.kbandroidtechassessment.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kbandroidtechassessment.models.Transaction
import com.example.kbandroidtechassessment.utils.parseDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: MainViewModel

    private val transactions = listOf(
        Transaction("2024-09-22", "Restaurant", -35.00),
        Transaction("2024-09-24", "Car Repair", -150.00),
        Transaction("2024-09-11", "Utilities", -150.00),
        Transaction("2024-09-19", "Clothing Store", -100.00),
        Transaction("2024-09-01", "Grocery Store", -75.00),
        Transaction("2024-09-30", "Gym Membership", -50.00)
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel()
        viewModel.apply {
            transactions.value = this@MainViewModelTest.transactions
        }
    }

    @Test
    fun `filteredTransactions within date range`() = runTest {
        viewModel.setDateRange("2024-09-11", "2024-09-22")

        val filteredTransactions = viewModel.filteredTransactions.first()
        val expectedTransactions = transactions.filter {
            val transactionDate = it.date.parseDate()
            val start = "2024-09-11".parseDate()
            val end = "2024-09-22".parseDate()
            transactionDate in start..end
        }

        assertEquals(expectedTransactions, filteredTransactions)
    }

    @Test
    fun `filteredTransactions with null date range returns all`() = runTest {
        viewModel.clearDateRange()

        val filteredTransactions = viewModel.filteredTransactions.first()
        assertEquals(transactions, filteredTransactions)
    }

    @Test
    fun `availableAmount calculation return correct value`() = runTest {
        viewModel.setDateRange("2024-09-11", "2024-09-24")

        val availableAmount = viewModel.availableAmount.first()
        val expectedAmount = transactions.filter {
            val transactionDate = it.date.parseDate()
            val start = "2024-09-11".parseDate()
            val end = "2024-09-24".parseDate()
            transactionDate in start..end
        }.sumOf { it.amount }

        assertEquals(expectedAmount, availableAmount, 0.01)
    }

    @Test
    fun `availableAmount with null date range returns total amount`() = runTest {
        viewModel.clearDateRange()

        val availableAmount = viewModel.availableAmount.first()
        val expectedAmount = transactions.sumOf { it.amount }

        assertEquals(expectedAmount, availableAmount, 0.01)
    }

    @Test
    fun `setDateRange updates state`() = runTest {
        viewModel.setDateRange("2024-09-01", "2024-09-30")
        val dateRange = viewModel.selectedDateRange.first()
        assertEquals(Pair("2024-09-01", "2024-09-30"), dateRange)
    }

    @Test
    fun `clearDateRange updates state`() = runTest {
        viewModel.setDateRange("2024-09-01", "2024-09-30")
        viewModel.clearDateRange()
        val dateRange = viewModel.selectedDateRange.first()
        assertEquals(null, dateRange)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}