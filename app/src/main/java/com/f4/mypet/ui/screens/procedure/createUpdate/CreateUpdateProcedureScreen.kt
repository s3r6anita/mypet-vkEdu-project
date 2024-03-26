package com.f4.mypet.ui.screens.procedure.createUpdate


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.ui.components.MyPetTopBar
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
    profileId: Int,
    procedureId: Int = -1
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val viewModel: CreateUpdateProcedureViewModel = hiltViewModel()
    scope.launch {
        viewModel.getPetProcedure(procedureId)
    }
    val procedureDB by viewModel.procedureUiState.collectAsState()
    val titles = viewModel.titles
    val types = viewModel.types
    Log.d("types", types.toString())
    val titleDB = titles.find { title -> title.id == procedureDB.title }
    val typeDB = types.find { type -> type.id == (titleDB?.type ?: 0) }
    var procedure by remember {
        mutableStateOf(procedureDB)
    }
    var title by remember {
        mutableStateOf(titleDB)
    }
    var type by remember {
        mutableStateOf(typeDB)
    }
    LaunchedEffect(procedureDB) {
        procedure = procedureDB
        title = titleDB
        type = typeDB
    }


    var mutableProc by remember {
        mutableStateOf(
            @Suppress("MagicNumber")
            Procedure(
                1, // название
                1, // выполнена ли
                1, // частота выполнения
                LocalDateTime.now(), // когда выполнена
                LocalDateTime.now(), // когда создана
                "Заметки", // заметки
                LocalDateTime.now(), // время уведомлений
                1, // питомец
                1, // нужно ли добавить в медкарту
            )
        )
    }

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(/* TODO Routes.CreateProcedure.title*/ R.string.creation_procedure_screen_title),
                canNavigateBack = true,
                navigateUp = { /*TODO navController.navigateUp()*/ },
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
            val typeOptions =
                listOf("Гигиеническая", "Медицинская", "Пользовательская") // TODO: получение из VM
            var typeExpanded by remember { mutableStateOf(false) }
            var selectedType by remember { mutableStateOf(typeOptions[0]) }

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
                    value = selectedType,
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
                    typeOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                selectedType = selectionOption
                                typeExpanded = false
                            }
                        )
                    }
                }
            }

            // Название процедуры - выпадающее меню с выбором
            val nameOptions = when (selectedType) { // TODO: получать из VM
                "Гигиеническая" -> listOf(
                    "Стрижка шерсти",
                    "Стрижка когтей",
                    "Чистка зубов",
                    "Обработка лап"
                )

                "Медицинская" -> listOf(
                    "Прием лекарств и витаминов",
                    "Вакцинация",
                    "Дегельминтизация",
                    "Обработка от блох"
                )

                else -> emptyList<String>()
            }
            var nameExpanded by remember { mutableStateOf(false) }
            var selectedName by remember { mutableStateOf("") }

            if (nameOptions != emptyList<String>()) {
                // если не пользовательский тип процедуры
                ExposedDropdownMenuBox(
                    expanded = nameExpanded,
                    onExpandedChange = {
                        nameExpanded = it
                    },
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    TextField(
                        modifier = Modifier
                            .menuAnchor()
                            .padding(bottom = 10.dp),
                        readOnly = true,
                        value = selectedName,
                        onValueChange = { },
                        label = { Text(stringResource(R.string.creation_procedure_screen_name)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = nameExpanded
                            )
                        },
                    )
                    ExposedDropdownMenu(
                        expanded = nameExpanded,
                        onDismissRequest = {
                            nameExpanded = false
                        }
                    ) {
                        nameOptions.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    selectedName = selectionOption
                                    nameExpanded = false
                                }
                            )
                        }
                    }
                }
            } else {
                // если пользовательский тип процедуры
                OutlinedTextField(
                    value = selectedName,
                    onValueChange = {
                        selectedName = it
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
                    onValueChange = { frequencyString = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    label = { Text(text = stringResource(R.string.creation_procedure_screen_period)) },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { frequencyString = "" }) {
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
            val state = rememberTimePickerState()
            //TODO разобраться с форматом времени (уже по известным данным из БД)
            var timeString by remember {
                mutableStateOf(
                    mutableProc.dateCreated.format(
                        PetDateTimeFormatter.time
                    )
                )
            }

            OutlinedTextField(
                value = timeString,
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
                modifier = modifier
            )
            if (openTimeDialog) {
                AlertDialog(
                    title = {
                        Text(text = stringResource(id = R.string.creation_procedure_screen_pick_time))
                    },
                    text = { TimePicker(state = state) },
                    onDismissRequest = { openTimeDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            timeString = "${state.hour}:${state.minute}"
                            openTimeDialog = false
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
            //TODO разобраться с форматом времени (уже по известным данным из БД)
            var openDateDialog by remember { mutableStateOf(false) }
            var dateString by remember {
                mutableStateOf(
                    mutableProc.dateCreated.format(
                        PetDateTimeFormatter.date
                    )
                )
            }

            OutlinedTextField(
                value = dateString,
                onValueChange = {
                    //TODO const val CORRECT_DATE_DIGIT_NUMBER = 10???
                    if (it.length <= CORRECT_DATE_DIGIT_NUMBER) dateString = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                label = { Text(stringResource(id = R.string.creation_procedure_screen_date_of_completion)) },
                supportingText = { Text(text = stringResource(id = R.string.date_format)) },
                trailingIcon = {
                    IconButton(onClick = { openDateDialog = true }) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = stringResource(id = R.string.creation_procedure_screen_open_calendar)
                        )
                    }
                },
                modifier = modifier
            )
            if (openDateDialog) {
                val datePickerState = rememberDatePickerState()
                DatePickerDialog(
                    onDismissRequest = {
                        openDateDialog = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openDateDialog = false
                                dateString =
                                    (LocalDateTime.ofInstant(
                                        Instant.ofEpochMilli(
                                            datePickerState.selectedDateMillis ?: 0
                                        ),
                                        ZoneId.of("UTC")
                                    )).format(PetDateTimeFormatter.date)
                            },
                        ) {
                            Text(stringResource(id = R.string.confirm_button_description))
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { openDateDialog = false }
                        ) {
                            Text(stringResource(id = R.string.cancel_button_description))
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            // уведомление
            var enableNotifications by remember { mutableStateOf(false) }
            var timeNotificationString by remember { mutableStateOf("") }

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
                    text = "Уведомления",
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
                value = mutableProc.notes,
                onValueChange = { mutableProc = mutableProc.copy(notes = it) },
                label = { Text(stringResource(id = R.string.creation_procedure_screen_notes)) },
                trailingIcon = {
                    IconButton(onClick = { mutableProc = mutableProc.copy(notes = "") }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.clear)
                        )
                    }
                },
                modifier = modifier
                    .height(120.dp)
            )

            // сохранение
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    try {
                        //TODO Проверка на формат даты и на "дату из будущего"
                        if (selectedName == "") {
                            throw IllegalArgumentException("Некорректное название")
                        }

                        //TODO изменение полей на основе полученных значений

                        //TODO добавление в список процедур

                        // TODO: SnackBar

                        //TODO nav...
                    } catch (e: IllegalArgumentException) {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Text(text = "Сохранить")
            }
        }
    }
}
