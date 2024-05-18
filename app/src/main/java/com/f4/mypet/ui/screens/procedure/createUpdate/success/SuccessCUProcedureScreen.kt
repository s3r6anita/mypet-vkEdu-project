package com.f4.mypet.ui.screens.procedure.createUpdate.success

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.PetDateTimeFormatter
import com.f4.mypet.PresentOrFutureSelectableDates
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.screens.procedure.createUpdate.CreateUpdateProcedureViewModel
import com.f4.mypet.ui.theme.BlueCheckbox
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.LightBlueBackground
import com.f4.mypet.ui.theme.LightGrayTint
import com.f4.mypet.ui.theme.White
import com.f4.mypet.validate
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Suppress("CyclomaticComplexMethod", "LongMethod")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessCUProcedureScreen(
    navController: NavHostController,
    isCreateScreen: Boolean,
    profileId: Int,
    viewModel: CreateUpdateProcedureViewModel = hiltViewModel()
) {
    //TODO сделать update когда меняем тип, то есть с insert в таблицу title
    val titles = viewModel.titles
    val types = viewModel.types
    val options = viewModel.options
    val procedureDB by viewModel.procedureUiState.collectAsState()

    val type by remember {
        mutableStateOf(viewModel.type)
    }
    var title by remember {
        mutableStateOf(viewModel.title)
    }
    var frequency by remember {
        mutableStateOf(viewModel.frequency)
    }
    var procedure by remember {
        mutableStateOf(procedureDB)
    }
    LaunchedEffect(procedureDB) {
        procedure = procedureDB
    }

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = if (isCreateScreen) stringResource(Routes.CreateProcedure.title) else stringResource(
                    Routes.UpdateProcedure.title
                ),
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() },
                actions = {}
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // выбор типа процедуры в отдельной функции, по аналогии нужно другие выборы вынести
            var selectedType by remember {
                mutableStateOf(
                    if (isCreateScreen) types.first()
                    else type
                )
            }
            SelectProcedureType(
                types = types,
                selectedType = selectedType,
                dropdownMenuColors = getDropdownMenuColors(),
                changeSelectedType = { newType ->
                    selectedType = newType
                    title = title.copy(type = newType.id)
                },
            )

            // Название процедуры
            var titleIsCorrect by remember { mutableStateOf(!isCreateScreen) }
            // TODO: сделать чтобы при вводе тайтла выпадали последние введенные

            OutlinedTextField(
                value = title.name,
                onValueChange = {
                    titleIsCorrect = validate(it)
                    title = title.copy(name = it)
                },
                label = { Text(stringResource(R.string.creation_procedure_screen_name)) },
                modifier = Modifier
                    .padding(bottom = 10.dp, start = 30.dp, end = 30.dp)
                    .fillMaxWidth(),
                isError = !titleIsCorrect,
            )

            // периодичность - выпадающее меню с выбором
            var frequencyExpanded by remember { mutableStateOf(false) }
            var selectedFrequency by remember {
                mutableStateOf(
                    if (isCreateScreen) options[0]
                    else viewModel.frequency.option
                )
            }
            var frequencyString by remember { mutableStateOf(frequency.frequency) }


            ExposedDropdownMenuBox(
                expanded = frequencyExpanded,
                onExpandedChange = {
                    frequencyExpanded = it
                },
                modifier = Modifier.padding(bottom = 5.dp)
            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(bottom = 15.dp),
                    value = selectedFrequency,
                    readOnly = true,
                    label = { Text(stringResource(R.string.creation_procedure_screen_frequence)) },
                    onValueChange = { },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = frequencyExpanded)
                    },
                    colors = getDropdownMenuColors(),
                )
                ExposedDropdownMenu(
                    expanded = frequencyExpanded,
                    onDismissRequest = {
                        frequencyExpanded = false
                    }
                ) {
                    options.forEach() { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                selectedFrequency = selectionOption
                                frequency = frequency.copy(option = selectionOption)
                                frequencyExpanded = false
                            }
                        )
                    }
                }
            }
            if (selectedFrequency != options[0]) {
                OutlinedTextField(
                    value = frequencyString,
                    onValueChange = {
                        frequencyString = it
                        frequency = frequency.copy(frequency = frequencyString)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    label = { Text(text = stringResource(R.string.creation_procedure_screen_period)) },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            frequencyString = ""
                        }) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = stringResource(id = R.string.clear)
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = getOutLinedTextFieldColors()
                )
            }

            // Время выполнения - тайм пикер
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
                                procedure = procedure.copy(
                                    dateDone = procedure.dateDone
                                        .withHour(timePickerState.hour)
                                        .withMinute(timePickerState.minute)
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

            // дата выполнения
            var openDateDialog by remember { mutableStateOf(false) }
            val datePickerState =
                rememberDatePickerState(selectableDates = PresentOrFutureSelectableDates)
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
                                procedure = procedure.copy(
                                    dateDone = LocalDateTime.ofInstant(
                                        Instant.ofEpochMilli(
                                            datePickerState.selectedDateMillis ?: 0
                                        ),
                                        ZoneId.of("UTC")
                                    )
                                        .withHour(procedure.dateDone.hour)
                                        .withMinute(procedure.dateDone.minute)
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

            // уведомление
            var enableNotifications by remember { mutableStateOf(false) }
            enableNotifications = (procedure.reminder != null) && (procedure.reminder?.let {
                procedure.reminder!!.format(PetDateTimeFormatter.dateTime)
            } != "01.01.1001 00:00")
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
                            procedure = procedure.copy(reminder = null)
                        else if (procedure.reminder == null && it)
                            procedure = procedure.copy(
                                reminder = procedure.dateDone
                                    .minusDays(1).withMinute(0)
                            )
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
                var timeReminderIsCorrect by remember { mutableStateOf(true) }

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
                                    procedure = procedure.copy(
                                        reminder = procedure.reminder!!
                                            .withHour(timeReminderPickerState.hour)
                                            .withMinute(timeReminderPickerState.minute)
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
                val dateReminderPickerState =
                    rememberDatePickerState(selectableDates = PresentOrFutureSelectableDates)
                var dateReminderIsCorrect by remember { mutableStateOf(true) }

                OutlinedTextField(
                    value = procedure.reminder!!.format(PetDateTimeFormatter.date),
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
                                    procedure = procedure.copy(
                                        reminder = LocalDateTime.ofInstant(
                                            Instant.ofEpochMilli(
                                                dateReminderPickerState.selectedDateMillis ?: 0
                                            ),
                                            ZoneId.of("UTC")
                                        )
                                            .withHour(procedure.reminder!!.hour)
                                            .withMinute(procedure.reminder!!.minute)
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

            // заметки
            OutlinedTextField(
                value = procedure.notes,
                onValueChange = { procedure = procedure.copy(notes = it) },
                label = { Text(stringResource(id = R.string.creation_procedure_screen_notes)) },
                trailingIcon = {
                    IconButton(onClick = { procedure = procedure.copy(notes = "") }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.clear)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                singleLine = false,
                shape = RoundedCornerShape(12.dp),
                colors = getOutLinedTextFieldColors()
            )

            // сохранение
            Button(
                modifier = Modifier.padding(20.dp),
                onClick = {
                        //TODO Проверка на формат даты и на "дату из будущего"

                        if (isCreateScreen) {
                            procedure = procedure.copy(pet = profileId)
                            viewModel.createProcedure(procedure, title, frequency)
                            navController.navigateUp()
                        } else {
                            viewModel.updateProcedure(procedure, title, frequency)
                            navController.navigateUp()
                        }
                },
                border = BorderStroke(1.dp, GreenButton),
                colors = ButtonDefaults.buttonColors(containerColor = GreenButton)
            ) {
                Text(
                    text = stringResource(id = R.string.save_button_description),
                    Modifier.padding(start = 10.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
