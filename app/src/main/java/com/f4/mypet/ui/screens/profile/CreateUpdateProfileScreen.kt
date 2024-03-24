package com.f4.mypet.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.f4.mypet.PastOrPresentSelectableDates
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.dateFormat
import com.f4.mypet.navigation.Routes
import com.f4.mypet.ui.ClearIcon
import com.f4.mypet.ui.CustomSnackBar
import com.f4.mypet.ui.MyPetTopBar
import com.f4.mypet.ui.SHOWSNACKDURATION
import com.f4.mypet.validate
import com.f4.mypet.validateMicrochipNumber
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


@Composable
@OptIn(ExperimentalMaterial3Api::class)
@Suppress("CyclomaticComplexMethod", "LongMethod")
fun CreateUpdateProfileScreen(
    navController: NavHostController,
    isCreateScreen: Boolean,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    profileId: Int? = -1
) {
    val context = LocalContext.current

    var pet by remember {
        mutableStateOf(
            if (isCreateScreen) {
                Pet(
                    "", "", "", "Самец", LocalDateTime.of(
                        LocalDate.now(),
                        LocalTime.now()
                    ), "", "", ""
                )
            } else {
                // TODO: поменять на данные, получаемые из ViewModel
                Pet(
                    "", "", "", "Самец", LocalDateTime.of(
                        LocalDate.now(),
                        LocalTime.now()
                    ), "", "", ""
                )
            }
        )
    }

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(
                    if (isCreateScreen)
                        Routes.CreateProfile.title
                    else
                        Routes.UpdateProfile.title
                ),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                actions = { }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) {
                CustomSnackBar(it.visuals.message)
            }
        }
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
            // пол
            val radioOptions = listOf("Самец", "Самка")
            val selectedOption by remember {
                mutableStateOf(radioOptions[0])
            }

            Text(
                text = stringResource(id = R.string.create_profile_sex),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
            Row(
                Modifier
                    .selectableGroup()
                    .padding(vertical = 8.dp)
            ) {
                radioOptions.forEach { text ->
                    Column(
                    ) {
                        Row(
                            modifier = Modifier
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = { pet = pet.copy(sex = text) },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                        ) {
                            RadioButton(
                                selected = (text == pet.sex),
                                onClick = null
                            )
                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }
            // кличка
            var nameIsCorrect by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = pet.name,
                singleLine = true,
                onValueChange = {
                    nameIsCorrect = validate(it)
                    pet = pet.copy(name = it)
                },
                label = { Text(stringResource(id = R.string.pet_nickname)) },
                trailingIcon = {
                    ClearIcon {
                        nameIsCorrect = false
                        pet = pet.copy(name = "")
                    }
                },
                supportingText = {
                    if (!nameIsCorrect && pet.name != "") Text(stringResource(id = R.string.create_profile_supp))
                },
                isError = !nameIsCorrect,
                modifier = Modifier.padding(top = 5.dp)
            )
            // вид
            var kindIsCorrect by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = pet.kind,
                singleLine = true,
                onValueChange = {
                    kindIsCorrect = validate(it)
                    pet = pet.copy(kind = it)
                },
                label = { Text(stringResource(id = R.string.pet_view)) },
                trailingIcon = {
                    ClearIcon {
                        kindIsCorrect = false
                        pet = pet.copy(kind = "")
                    }
                },
                supportingText = {
                    if (!kindIsCorrect && pet.kind != "") Text(stringResource(id = R.string.create_profile_supp))
                },
                isError = !kindIsCorrect,
                modifier = Modifier.padding(top = 5.dp)
            )
            // порода
            var breedIsCorrect by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = pet.breed,
                singleLine = true,
                onValueChange = {
                    breedIsCorrect = validate(it)
                    pet = pet.copy(breed = it)
                },
                label = { Text(stringResource(id = R.string.pet_breed)) },
                trailingIcon = {
                    ClearIcon {
                        breedIsCorrect = false
                        pet = pet.copy(breed = "")
                    }
                },
                supportingText = {
                    if (!breedIsCorrect && pet.breed != "") Text(stringResource(id = R.string.create_profile_supp))
                },
                isError = !breedIsCorrect,
                modifier = Modifier.padding(top = 5.dp)
            )
            // шерсть
            var coatIsCorrect by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = pet.coat,
                singleLine = true,
                onValueChange = {
                    coatIsCorrect = validate(it)
                    pet = pet.copy(coat = it)
                },
                label = { Text(stringResource(id = R.string.pet_coat)) },
                trailingIcon = {
                    ClearIcon {
                        coatIsCorrect = false
                        pet = pet.copy(coat = "")
                    }
                },
                supportingText = {
                    if (!coatIsCorrect && pet.coat != "") Text(stringResource(id = R.string.create_profile_supp))
                },
                isError = !coatIsCorrect,
                modifier = Modifier.padding(top = 5.dp)
            )
            // окрас
            var colorIsCorrect by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = pet.color,
                singleLine = true,
                onValueChange = {
                    colorIsCorrect = validate(it)
                    pet = pet.copy(color = it)
                },
                label = { Text(stringResource(id = R.string.pet_color)) },
                trailingIcon = {
                    ClearIcon {
                        colorIsCorrect = false
                        pet = pet.copy(coat = "")
                    }
                },
                supportingText = {
                    if (!colorIsCorrect && pet.color != "") Text(stringResource(id = R.string.create_profile_supp))
                },
                isError = !colorIsCorrect,
                modifier = Modifier.padding(top = 5.dp)
            )

            // дата рождения
            var openDialog by remember { mutableStateOf(false) }
            val datePickerState =
                rememberDatePickerState(selectableDates = PastOrPresentSelectableDates)
            var dateIsCorrect by remember { mutableStateOf(true) }

            OutlinedTextField(
                value = dateFormat.format(pet.birthday),
                onValueChange = { },
                label = { Text(stringResource(id = R.string.pet_birthday)) },
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
                modifier = Modifier.padding(top = 5.dp)
            )
            if (openDialog) {
                DatePickerDialog(
                    onDismissRequest = {
                        openDialog = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
//                                openDialog = false
//                                pet = pet.copy(
//                                    birthday = Date(datePickerState.selectedDateMillis ?: 0)
//                                )
//                                try {
//                                    dateIsCorrect =
//                                        validateBirthday(dateFormat.format(pet.birthday))
//                                } catch (e: IllegalArgumentException) {
//                                    scope.launch {
//                                        snackbarHostState.showSnackbar(
//                                            e.message
//                                                ?: context.resources.getString(R.string.incorrect_date)
//                                        )
//                                    }
//                                }
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

            // номер микрочипа
            var microchipNumberIsCorrect by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = pet.microchipNumber,
                singleLine = true,
                onValueChange = {
                    microchipNumberIsCorrect = validateMicrochipNumber(it)
                    pet = pet.copy(microchipNumber = it)
                },
                label = { Text(stringResource(id = R.string.pet_microchip)) },
                trailingIcon = {
                    ClearIcon {
                        microchipNumberIsCorrect = false
                        pet = pet.copy(microchipNumber = "")
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                supportingText = {
                    if (!microchipNumberIsCorrect && pet.microchipNumber != "") Text(
                        stringResource(
                            id = R.string.create_profile_supp_chip
                        )
                    )
                },
                isError = !microchipNumberIsCorrect,
                modifier = Modifier.padding(top = 5.dp)
            )

            // сохранение
            Button(
                modifier = Modifier.padding(16.dp),
                enabled = nameIsCorrect && kindIsCorrect && breedIsCorrect &&
                        coatIsCorrect && colorIsCorrect && dateIsCorrect && microchipNumberIsCorrect,
                onClick = {
                    //TODO: добавление в питомца в БД

                    scope.launch {
                        val job = launch {
                            snackbarHostState.showSnackbar(
                                if (isCreateScreen)
                                    context.resources.getString(R.string.create_profile_successful_pet_creation)
                                else
                                    context.resources.getString(R.string.create_profile_successful_pet_update)
                            )
                        }
                        delay(SHOWSNACKDURATION)
                        job.cancel()
                    }

                    if (isCreateScreen) {
                        navController.navigate(Routes.ListProfile.route) {
                            popUpTo(Routes.ListProfile.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigateUp()
                    }
                }
            ) {
                Text(text = stringResource(id = R.string.save_button_description))
            }
        }
    }
}
