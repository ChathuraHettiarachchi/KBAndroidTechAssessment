package com.example.kbandroidtechassessment.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.kbandroidtechassessment.data.getTransactions
import com.example.kbandroidtechassessment.models.Transaction
import com.example.kbandroidtechassessment.ui.theme.KBAndroidTechAssessmentTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KBAndroidTechAssessmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        LargeTopAppBar(
                            title = {
                                Text("Travel Savings Account")
                            },
                            actions = {
                                IconButton(onClick = { /* Handle filter action */ }) {
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
                                text = "$0.00",
                                fontWeight = FontWeight.W600,
                                fontSize = TextUnit(24f, TextUnitType.Sp),
                            )
                        }
                        HorizontalDivider()
                        val transactions = getTransactions()
                        LazyColumn {
                            items(transactions) { transaction ->
                                TransactionItem(transaction = transaction)
                            }
                        }
                    }
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