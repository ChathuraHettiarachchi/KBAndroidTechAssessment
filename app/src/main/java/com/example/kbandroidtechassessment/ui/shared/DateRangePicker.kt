package com.example.kbandroidtechassessment.ui.shared

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.kbandroidtechassessment.utils.DATE_FORMAT
import com.example.kbandroidtechassessment.utils.convertMillisToDate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePicker(
    showDatePicker: Boolean = false,
    initialDateRange: Pair<String, String>? = null,
    onDismiss: () -> Unit,
    onDateRangeSelected: (String, String) -> Unit
) {
    val pickerState = rememberDateRangePickerState()
    val context = LocalContext.current

    LaunchedEffect(initialDateRange) {
        if (initialDateRange == null) {
            pickerState.setSelection(startDateMillis = null, endDateMillis = null)
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = {
                        if (pickerState.selectedStartDateMillis != null && pickerState.selectedEndDateMillis != null) {
                            onDateRangeSelected(
                                pickerState.selectedStartDateMillis.convertMillisToDate(),
                                pickerState.selectedEndDateMillis.convertMillisToDate(),
                            )
                            onDismiss()
                        } else {
                            Toast.makeText(
                                context,
                                "Please select both start and end dates.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        ) {
            DateRangePicker(
                state = pickerState,
                title = {
                    Text(
                        text = "Select date range"
                    )
                },
                headline = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Box(Modifier.weight(1f)) {
                            (if (pickerState.selectedStartDateMillis != null) pickerState.selectedStartDateMillis?.let {
                                it.convertMillisToDate()
                            } else "Start Date")?.let { Text(text = it) }
                        }
                        Box(Modifier.weight(1f)) {
                            (if (pickerState.selectedEndDateMillis != null) pickerState.selectedEndDateMillis?.let {
                                it.convertMillisToDate()
                            } else "End Date")?.let { Text(text = it) }
                        }
                    }
                },
                showModeToggle = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(16.dp)
            )
        }
    }
}