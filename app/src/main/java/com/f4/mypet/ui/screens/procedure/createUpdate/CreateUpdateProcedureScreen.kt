package com.f4.mypet.ui.screens.procedure.createUpdate


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.f4.mypet.ui.screens.ErrorScreen
import com.f4.mypet.ui.screens.LoadingScreen
import com.f4.mypet.validate
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

const val CORRECT_DATE_DIGIT_NUMBER = 10

@Suppress("CyclomaticComplexMethod", "LongMethod")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUpdateProcedureScreen(
    navController: NavHostController,
    isCreateScreen: Boolean,
    procedureId: Int = -1
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewModel: CreateUpdateProcedureViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getPetProcedure(procedureId)
        }
    }
    when (uiState) {
        UiState.Loading -> LoadingScreen()
        UiState.Success -> {

            val titles = viewModel.titles
            val types = viewModel.types
            val procedureDB by viewModel.procedureUiState.collectAsState()

            var type by remember {
                mutableStateOf(viewModel.type)
            }
            var title by remember {
                mutableStateOf(viewModel.title)
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
                        text = stringResource(Routes.CreateProcedure.title),
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
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    val modifier = Modifier
                        .padding(top = 10.dp, start = 30.dp, end = 30.dp)
                        .fillMaxWidth()

                    // Тип процедуры - выпадающее меню с выбором
                    var selectedType by remember {
                        mutableStateOf(
                            if (isCreateScreen) types[1] // косметический тип
                            else type
                        )
                    }
                    var typeExpanded by remember { mutableStateOf(false) }

                    ExposedDropdownMenuBox(
                        expanded = typeExpanded,
                        onExpandedChange = {
                            typeExpanded = it
                        },
                        modifier = Modifier.padding(vertical = 10.dp)

                    ) {
                        TextField(
                            modifier = Modifier
                                .menuAnchor()
                                .padding(bottom = 10.dp),
                            readOnly = true,
                            value = selectedType.name,
                            onValueChange = { },
                            label = { Text(stringResource(id = R.string.creation_procedure_screen_procedure_type)) },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = typeExpanded
                                )
                            }
                        )
                        ExposedDropdownMenu(
                            expanded = typeExpanded,
                            onDismissRequest = {
                                typeExpanded = false
                            }
                        ) {
                            types.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption.name) },
                                    onClick = {
                                        selectedType = selectionOption
                                        typeExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Название процедуры
                    val titleOptions = titles.filter {
                        it.type == selectedType.id
                    }
                    var selectedTitle by remember {
                        mutableStateOf(
                            if (isCreateScreen) titleOptions[0]
                            else title
                        )
                    }
                    var titleExpanded by remember { mutableStateOf(false) }
                    var titleIsCorrect by remember { mutableStateOf(!isCreateScreen) }

                    // если процедура не "Косметического" типа
                    if (titleOptions != emptyList<String>()) {
                        ExposedDropdownMenuBox(
                            expanded = titleExpanded,
                            onExpandedChange = {
                                titleExpanded = it
                            },
                            modifier = Modifier.padding(bottom = 10.dp)
                        ) {
                            TextField(
                                modifier = Modifier
                                    .menuAnchor()
                                    .padding(bottom = 10.dp),
                                value = selectedTitle.name,
                                readOnly = true,
                                onValueChange = { },
                                label = { Text(stringResource(R.string.creation_procedure_screen_name)) },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = titleExpanded
                                    )
                                },
                            )
                            ExposedDropdownMenu(
                                expanded = titleExpanded,
                                onDismissRequest = {
                                    titleExpanded = false
                                }
                            ) {
                                titleOptions.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        text = { Text(selectionOption.name) },
                                        onClick = {
                                            selectedTitle = selectionOption
                                            titleExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    } else {
                        // если не медицинский тип процедуры
                        OutlinedTextField(
                            value = title.name,
                            onValueChange = {
                                titleIsCorrect = validate(it)
                                title = title.copy(name = it)
                            },
                            label = { Text(stringResource(R.string.creation_procedure_screen_name)) },
                            modifier = Modifier
                                .padding(bottom = 10.dp, start = 30.dp, end = 30.dp)
                                .fillMaxWidth()
                        )
                    }

                    // периодичность - выпадающее меню с выбором
                    var frequencyExpanded by remember { mutableStateOf(false) }
                    var selectedFrequency by remember { mutableStateOf(FrequencyOptions.Never) }
                    var frequencyString by remember { mutableStateOf("") }

                    ExposedDropdownMenuBox(
                        expanded = frequencyExpanded,
                        onExpandedChange = {
                            frequencyExpanded = it
                        },
                        modifier = Modifier.padding(bottom = 5.dp)
                    ) {
                        TextField(
                            value = selectedFrequency.period,
                            readOnly = true,
                            label = { Text(stringResource(R.string.creation_procedure_screen_frequence)) },
                            onValueChange = { },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = frequencyExpanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .padding(bottom = 10.dp),
                        )
                        ExposedDropdownMenu(
                            expanded = frequencyExpanded,
                            onDismissRequest = {
                                frequencyExpanded = false
                            }
                        ) {
                            FrequencyOptions.entries.forEach() { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption.period) },
                                    onClick = {
                                        selectedFrequency = selectionOption
                                        frequencyExpanded = false
                                    }
                                )
                            }
                        }
                    }
                    if (selectedFrequency != FrequencyOptions.Never) {
                        OutlinedTextField(
                            value = frequencyString,
                            onValueChange = {
                                frequencyString = it
                                if (it != "") {
                                    procedure = procedure.copy(
                                        frequency = when (selectedFrequency) {
                                            FrequencyOptions.Hours -> frequencyString.toInt()
                                            FrequencyOptions.Days -> frequencyString.toInt() * 24
                                            FrequencyOptions.Weeks -> frequencyString.toInt() * 24 * 7
                                            else -> 0
                                        }
                                    )
                                } else {
                                    procedure = procedure.copy(frequency = 0)
                                }
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
                            modifier = modifier.padding(bottom = 10.dp)
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
                        modifier = modifier
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
//                                        TODO: find correct format
//                                        procedure = procedure.copy(
//                                            dateDone = LocalDateTime.ofInstant(
//                                                Instant.ofEpochMilli(
////                                                    timePickerState.hour ?: 0
////                                                ),
//                                                ZoneId.of("UTC")
//                                            )
//                                        )
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
                        modifier = modifier
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
                    var enableNotifications by remember { mutableStateOf(procedure.reminder != null) }
                    var timeNotificationString by remember { mutableStateOf(procedure.reminder.toString()) } // временно
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, bottom = 10.dp)
                            .toggleable(
                                value = enableNotifications,
                                onValueChange = {
                                    enableNotifications = it
                                },
                                role = Role.Checkbox
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.cu_screen_notifications),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                        Checkbox(
                            checked = enableNotifications,
                            onCheckedChange = null,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    if (enableNotifications) {
                        OutlinedTextField(
                            value = timeNotificationString,
                            onValueChange = { timeNotificationString = it },
                            label = { Text(stringResource(id = R.string.creation_procedure_screen_time_before_notification)) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            trailingIcon = {
                                IconButton(onClick = { timeNotificationString = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = null)
                                }
                            }
                        )
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
                        modifier = modifier
                    )

                    // сохранение
                    Button(
                        modifier = Modifier.padding(16.dp),
                        onClick = {
                            try {
                                //TODO Проверка на формат даты и на "дату из будущего"

                                //TODO изменение полей на основе полученных значений

                                //TODO добавление в список процедур

                                if (isCreateScreen) {
//                                    viewModel.createProcedure(procedure)
                                    navController.navigate(Routes.BottomBarRoutes.ListProcedures.route) {
                                        popUpTo(Routes.BottomBarRoutes.ListProcedures.route) {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                } else {
//                                    viewModel.updateProcedure(procedure)
                                    navController.navigateUp()
                                }
                            } catch (e: IllegalArgumentException) {
                                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                            }
                        }
                    ) {
                        Text(text = stringResource(id = R.string.save_button_description))
                    }
                }
            }
        }

        else -> ErrorScreen(retryAction = viewModel::getPetProcedure, procedureId)
    }
}

