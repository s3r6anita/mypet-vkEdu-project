package com.f4.mypet.ui.screens.medcard.createUpdate.screenComponents

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.f4.mypet.R
import com.f4.mypet.ui.theme.OutlinedTextFieldColor
import com.f4.mypet.util.PetDateTimeFormatter
import com.f4.mypet.util.PresentOrFutureSelectableDates
import com.f4.mypet.util.validateDate
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedRecordDateField(
    isCreateScreen: Boolean,
    onDateSelected: (LocalDateTime) -> Unit,
    onDateIncorrect: (e: IllegalArgumentException) -> Unit,
    dbDate: LocalDateTime,
    modifier: Modifier = Modifier
) {
    var openDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(selectableDates = PresentOrFutureSelectableDates)
    var selectedDate by remember { mutableStateOf(dbDate) }
    var dateIsCorrect by remember {
        mutableStateOf(
            validateDate(
                selectedDate.format(
                    PetDateTimeFormatter.date
                )
            )
        )
    }
    var dateIsChosen by remember { mutableStateOf(false) }

    OutlinedTextField(
        //TODO: отформатировать дату
        value = if (dateIsChosen or !isCreateScreen) selectedDate.format(PetDateTimeFormatter.date) else "",
        onValueChange = {
            selectedDate = LocalDateTime.parse(it, PetDateTimeFormatter.date)
        },
        label = {
            if (isCreateScreen) Text(
                stringResource(R.string.cu_therapy_date),
                style = TextStyle(color = OutlinedTextFieldColor)
            ) else {
                Text(stringResource(R.string.cu_therapy_date)) //TODO подтягивание данных из БД
            }
        },
        supportingText = { Text(text = stringResource(id = R.string.date_format)) },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { openDialog = true }) {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = stringResource(id = R.string.show_calendar)
                )
            }
        },
        isError = !dateIsCorrect,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    )

    if (openDialog) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        selectedDate = LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(
                                datePickerState.selectedDateMillis ?: 0
                            ),
                            ZoneId.of("UTC")
                        )
                        dateIsChosen = true
                        onDateSelected(selectedDate)
                        try {
                            dateIsCorrect =
                                validateDate(selectedDate.format(PetDateTimeFormatter.date))
                        } catch (e: IllegalArgumentException) {
                            onDateIncorrect(e)
                        }
                    },
                ) {
                    Text(stringResource(id = R.string.confirm_button_description))
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog = false }) {
                    Text(stringResource(id = R.string.cancel_button_description))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
