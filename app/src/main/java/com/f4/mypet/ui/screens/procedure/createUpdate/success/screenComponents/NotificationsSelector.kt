package com.f4.mypet.ui.screens.procedure.createUpdate.success.screenComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.ui.theme.BlueCheckbox
import com.f4.mypet.ui.theme.LightBlueBackground
import com.f4.mypet.ui.theme.LightGrayTint
import com.f4.mypet.ui.theme.White
import com.f4.mypet.util.PetDateTimeFormatter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsSelector(
    procedure: Procedure,
    onProcedureChange: (Procedure) -> Unit
) {
    var enableNotifications by remember {
        mutableStateOf(((procedure.reminder?.let {
            procedure.reminder!!.format(PetDateTimeFormatter.dateTime)
        } != "01.01.1001 00:00")))
    }

    if (procedure.reminder == null)
        enableNotifications = false

    var openTimeDialog by remember { mutableStateOf(false) }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp)
            .toggleable(
                value = enableNotifications,
                onValueChange = {
                    enableNotifications = it
                },
                role = Role.Checkbox
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Switch(
            checked = enableNotifications,
            onCheckedChange = {
                if (!it)
                    onProcedureChange(procedure.copy(reminder = null))
                else if ((procedure.reminder == null ||
                            (procedure.reminder?.let {
                                procedure.reminder!!.format(PetDateTimeFormatter.dateTime)
                            }
                                    == "01.01.1001 00:00"))
                    && it
                ) {
                    onProcedureChange(
                        procedure.copy(
                            reminder = procedure.dateDone
                                .minusDays(1).withMinute(0)
                        )
                    )
                }
                enableNotifications = it
            },
            thumbContent = if (enableNotifications) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                        tint = White
                    )
                }
            } else {
                null
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = BlueCheckbox,
                checkedTrackColor = LightBlueBackground,
                uncheckedThumbColor = White,
                uncheckedTrackColor = LightGrayTint,
                uncheckedBorderColor = White
            )
        )

        Text(
            text = stringResource(id = R.string.cu_screen_notifications),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
    if (enableNotifications) {
        // Время напоминания - тайм пикер
        var openTimeReminderDialog by remember { mutableStateOf(false) }
        val timeReminderPickerState = rememberTimePickerState()
        val timeReminderIsCorrect by remember { mutableStateOf(true) }

        OutlinedTextField(
            value = procedure.reminder!!.format(PetDateTimeFormatter.time),
            onValueChange = { },
            readOnly = true,
            label = { Text(stringResource(id = R.string.creation_procedure_screen_duration)) },
            supportingText = { Text(text = stringResource(id = R.string.creation_procedure_screen_time_format)) },
            trailingIcon = {
                IconButton(
                    onClick = { openTimeReminderDialog = true }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_access_time),
                        contentDescription = stringResource(id = R.string.creation_procedure_screen_open_clock)
                    )
                }
            },
            isError = !timeReminderIsCorrect,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            shape = RoundedCornerShape(12.dp),
            colors = getOutLinedTextFieldColors()
        )
        if (openTimeReminderDialog) {
            AlertDialog(
                title = {
                    Text(text = stringResource(id = R.string.creation_procedure_screen_pick_time))
                },
                text = { TimePicker(state = timeReminderPickerState) },
                onDismissRequest = { openTimeReminderDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openTimeReminderDialog = false
                            onProcedureChange(
                                procedure.copy(
                                    reminder = procedure.reminder!!
                                        .withHour(timeReminderPickerState.hour)
                                        .withMinute(timeReminderPickerState.minute)
                                )
                            )
                            // TODO: catch Errors
                        }) {
                        Text(stringResource(id = R.string.procedure_screen_ok))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { openTimeDialog = false }) {
                        Text(stringResource(id = R.string.procedure_screen_cancel))
                    }
                }
            )
        }

        // дата напоминания
        var openDateReminderDialog by remember { mutableStateOf(false) }
        val dateReminderPickerState = rememberDatePickerState()
//        val dateReminderPickerState = rememberDatePickerState(selectableDates = PresentOrFutureSelectableDates)
        val dateReminderIsCorrect by remember { mutableStateOf(true) }

        OutlinedTextField(
            value = procedure.reminder.format(PetDateTimeFormatter.date),
            onValueChange = { },
            label = { Text(stringResource(id = R.string.creation_procedure_screen_date_of_completion)) },
            supportingText = { Text(text = stringResource(id = R.string.date_format)) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { openDateReminderDialog = true }) {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = stringResource(id = R.string.show_calendar)
                    )
                }
            },
            isError = !dateReminderIsCorrect,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            shape = RoundedCornerShape(12.dp),
            colors = getOutLinedTextFieldColors()
        )
        if (openDateReminderDialog) {
            DatePickerDialog(
                onDismissRequest = {
                    openDateReminderDialog = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDateReminderDialog = false
                            onProcedureChange(
                                procedure.copy(
                                    reminder = LocalDateTime.ofInstant(
                                        Instant.ofEpochMilli(
                                            dateReminderPickerState.selectedDateMillis ?: 0
                                        ),
                                        ZoneId.of("UTC")
                                    )
                                        .withHour(procedure.reminder.hour)
                                        .withMinute(procedure.reminder.minute)
                                )
                            )
                        },
                    ) {
                        Text(stringResource(id = R.string.confirm_button_description))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { openDateReminderDialog = false }) {
                        Text(stringResource(id = R.string.cancel_button_description))
                    }
                }
            ) {
                DatePicker(state = dateReminderPickerState)
            }
        }
    }
}
