package com.f4.mypet.ui.screens.medcard.createUpdate.screenComponents

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.f4.mypet.R
import com.f4.mypet.ui.theme.OutlinedTextFieldColor
import com.f4.mypet.util.PetDateTimeFormatter
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedRecordTimeField(
    isCreateScreen: Boolean,
    onDateSelected: (LocalDateTime) -> Unit,
    modifier: Modifier = Modifier,
    dbTime: LocalDateTime
) {
    var openDialog by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()
    var timeIsCorrect by remember { mutableStateOf(true) }
    var selectedTime by remember { mutableStateOf(dbTime) }
    var timeIsChosen by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = if (timeIsChosen or !isCreateScreen)
            selectedTime.format(PetDateTimeFormatter.time) else "",
        onValueChange = {
            selectedTime = LocalDateTime.parse(it, PetDateTimeFormatter.time)
        },
        label = {
            if (isCreateScreen) Text(
                stringResource(R.string.procedure_screen_time_of_event),
                style = TextStyle(color = OutlinedTextFieldColor)
            ) else {
                Text(stringResource(R.string.procedure_screen_time_of_event)) //TODO подтягивание данных из БД
            }
        },
        supportingText = { Text(text = stringResource(id = R.string.creation_procedure_screen_time_format)) },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { openDialog = true }) {
                Icon(
                    painter = painterResource(R.drawable.ic_access_time),
                    contentDescription = stringResource(id = R.string.creation_procedure_screen_open_clock)
                )
            }
        },
        isError = !timeIsCorrect,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    )

    if (openDialog) {
        AlertDialog(
            title = {
                Text(text = stringResource(id = R.string.creation_procedure_screen_pick_time))
            },
            text = { TimePicker(state = timePickerState) },
            onDismissRequest = { openDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedTime = selectedTime
                            .withHour(timePickerState.hour)
                            .withMinute(timePickerState.minute)
                        timeIsChosen = true
                        onDateSelected(selectedTime)
                        openDialog = false
                        //TODO копирование в БД
                        try {
                            //TODO валидирование
                        } catch (e: IllegalArgumentException) {
                            //TODO введена неккоректная дата
                        }
                    }) {
                    Text(stringResource(id = R.string.procedure_screen_ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog = false }) {
                    Text(stringResource(id = R.string.procedure_screen_cancel))
                }
            }
        )
    }

}
