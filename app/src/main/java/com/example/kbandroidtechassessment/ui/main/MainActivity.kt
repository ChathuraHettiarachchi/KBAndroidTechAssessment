package com.example.kbandroidtechassessment.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.kbandroidtechassessment.models.Transaction
import com.example.kbandroidtechassessment.ui.shared.DateRangePicker
import com.example.kbandroidtechassessment.ui.theme.Grey
import com.example.kbandroidtechassessment.ui.theme.KBAndroidTechAssessmentTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showDatePicker by remember { mutableStateOf(false) }
            val transactions by viewModel.filteredTransactions.collectAsState()
            val availableAmount by viewModel.availableAmount.collectAsState()
            val selectedDateRange by viewModel.selectedDateRange.collectAsState()

            KBAndroidTechAssessmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        LargeTopAppBar(
                            title = {
                                Text("Travel Savings Account")
                            },
                            actions = {
                                IconButton(onClick = {
                                    showDatePicker = true
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = "Filter"
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Row(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Available Balance:  ",
                                fontWeight = FontWeight.W300,
                                fontSize = TextUnit(16f, TextUnitType.Sp),
                            )
                            Text(
                                text = "$$availableAmount",
                                fontWeight = FontWeight.W600,
                                fontSize = TextUnit(24f, TextUnitType.Sp),
                            )
                        }
                        if (selectedDateRange != null) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(color = Grey)
                                    .padding(start = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Filter applied: ${selectedDateRange!!.first} - ${selectedDateRange!!.second}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                )
                                IconButton(onClick = {
                                    viewModel.clearDateRange()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        tint = Color.White,
                                        contentDescription = "Clear Filter"
                                    )
                                }
                            }
                        } else {
                            HorizontalDivider()
                        }
                        LazyColumn {
                            items(transactions) { transaction ->
                                TransactionItem(transaction = transaction)
                            }
                        }
                    }
                    DateRangePicker(
                        showDatePicker = showDatePicker,
                        onDismiss = { showDatePicker = false },
                        onDateRangeSelected = { startDate, endDate ->
                            viewModel.setDateRange(startDate, endDate)
                            showDatePicker = false
                        },
                        initialDateRange = selectedDateRange
                    )
                }
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column(modifier = Modifier.weight(1f)) { // Left side
            Text(text = transaction.description)
            Text(text = transaction.date, style = MaterialTheme.typography.labelSmall)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "$${transaction.amount}",
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
    }
}