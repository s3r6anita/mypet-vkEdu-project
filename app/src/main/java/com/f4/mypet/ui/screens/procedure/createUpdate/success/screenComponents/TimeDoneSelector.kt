package com.f4.mypet.ui.screens.procedure.createUpdate.success.screenComponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.util.PetDateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeDoneSelector(
    procedure: Procedure,
    onProcedureChange: (Procedure) -> Unit
) {
    var openTimeDialog by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()
    var timeIsCorrect by remember { mutableStateOf(true) }

    OutlinedTextField(
        value = procedure.dateDone.format(PetDateTimeFormatter.time),
        onValueChange = { },
        readOnly = true,
        label = { Text(stringResource(id = R.string.creation_procedure_screen_duration)) },
        supportingText = { Text(text = stringResource(id = R.string.creation_procedure_screen_time_format)) },
        trailingIcon = {
            IconButton(
                onClick = { openTimeDialog = true }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_access_time),
                    contentDescription = stringResource(id = R.string.creation_procedure_screen_open_clock)
                )
            }
        },
        isError = !timeIsCorrect,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        shape = RoundedCornerShape(12.dp),
        colors = getOutLinedTextFieldColors()
    )
    if (openTimeDialog) {
        AlertDialog(
            title = {
                Text(text = stringResource(id = R.string.creation_procedure_screen_pick_time))
            },
            text = { TimePicker(state = timePickerState) },
            onDismissRequest = { openTimeDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        openTimeDialog = false
                        onProcedureChange(
                            procedure.copy(
                                dateDone = procedure.dateDone
                                    .withHour(timePickerState.hour)
                                    .withMinute(timePickerState.minute)
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
}