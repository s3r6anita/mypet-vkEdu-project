package com.f4.mypet.ui.screens.procedure.createUpdate.success.screenComponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.util.PetDateTimeFormatter
import com.f4.mypet.util.PresentOrFutureSelectableDates
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerSelector(
    procedure: Procedure,
    onProcedureChange: (Procedure) -> Unit
) {
    var openDateDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(selectableDates = PresentOrFutureSelectableDates)
    var dateIsCorrect by remember { mutableStateOf(true) }

    OutlinedTextField(
        value = procedure.dateDone.format(PetDateTimeFormatter.date),
        onValueChange = { },
        label = { Text(stringResource(id = R.string.creation_procedure_screen_date_of_completion)) },
        supportingText = { Text(text = stringResource(id = R.string.date_format)) },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { openDateDialog = true }) {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = stringResource(id = R.string.show_calendar)
                )
            }
        },
        isError = !dateIsCorrect,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        shape = RoundedCornerShape(12.dp),
        colors = getOutLinedTextFieldColors()
    )
    if (openDateDialog) {
        DatePickerDialog(
            onDismissRequest = {
                openDateDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDateDialog = false
                        onProcedureChange(
                            procedure.copy(
                                dateDone = LocalDateTime.ofInstant(
                                    Instant.ofEpochMilli(datePickerState.selectedDateMillis ?: 0),
                                    ZoneId.of("UTC")
                                )
                                    .withHour(procedure.dateDone.hour)
                                    .withMinute(procedure.dateDone.minute)
                            )
                        )
                        // TODO: catch Errors
                    },
                ) {
                    Text(stringResource(id = R.string.confirm_button_description))
                }
            },
            dismissButton = {
                TextButton(onClick = { openDateDialog = false }) {
                    Text(stringResource(id = R.string.cancel_button_description))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
