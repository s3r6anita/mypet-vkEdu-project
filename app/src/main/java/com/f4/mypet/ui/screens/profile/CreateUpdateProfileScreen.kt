package com.f4.mypet.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.f4.mypet.PastOrPresentSelectableDates
import com.f4.mypet.R
import com.f4.mypet.dateFormat
import com.f4.mypet.db.entities.Pet
import com.f4.mypet.navigation.Routes
import com.f4.mypet.ui.components.MyPetSnackBar
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.components.SHOWSNACKDURATION
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.validate
import com.f4.mypet.validateBirthday
import com.f4.mypet.validateMicrochipNumber
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

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
                Pet("", "", "", true, Date(), "", "", "")
            } else {
                // TODO: поменять на данные, получаемые из ViewModel
                Pet("", "", "", true, Date(), "", "", "")
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
                navigateUp = { navController.navigateUp() }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) {
                MyPetSnackBar(it.visuals.message)
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)

            // пол
            val radioOptions = mapOf(
                stringResource(id = R.string.male_sex) to true,
                stringResource(id = R.string.female_sex) to false
            )
            Row(
                modifier
                    .selectableGroup()
                    .padding(top = 15.dp, bottom = 5.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.create_profile_sex),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
                )
                Spacer(modifier = Modifier
                    .height(10.dp)
                    .width(50.dp)
                )
                radioOptions.forEach { elem ->
                    Column(
                    ) {
                        Row(
                            modifier = Modifier
                                .selectable(
                                    selected = (elem.value == pet.sex),
                                    onClick = { pet = pet.copy(sex = elem.value) },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                        ) {
                            RadioButton(
                                selected = (elem.value == pet.sex),
                                onClick = null
                            )
                            Text(
                                text = elem.key,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 10.dp)
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
                shape = RoundedCornerShape(10.dp),
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
                modifier = modifier
            )
            // вид
            var kindIsCorrect by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = pet.kind,
                shape = RoundedCornerShape(10.dp),
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
                modifier = modifier
            )
            // порода
            var breedIsCorrect by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = pet.breed,
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
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
                modifier = modifier
            )
            // шерсть
            var coatIsCorrect by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = pet.coat,
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
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
                modifier = modifier
            )
            // окрас
            var colorIsCorrect by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = pet.color,
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
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
                modifier = modifier
            )

            // дата рождения
            var openDialog by remember { mutableStateOf(false) }
            val datePickerState =
                rememberDatePickerState(selectableDates = PastOrPresentSelectableDates)
            var dateIsCorrect by remember { mutableStateOf(true) }

            OutlinedTextField(
                value = dateFormat.format(pet.birthday),
                onValueChange = { },
                shape = RoundedCornerShape(10.dp),
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
                modifier = modifier
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
                                pet = pet.copy(
                                    birthday = Date(datePickerState.selectedDateMillis ?: 0)
                                )
                                try {
                                    dateIsCorrect =
                                        validateBirthday(dateFormat.format(pet.birthday))
                                } catch (e: IllegalArgumentException) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            e.message
                                                ?: context.resources.getString(R.string.incorrect_date)
                                        )
                                    }
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

            // номер микрочипа
            var microchipNumberIsCorrect by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = pet.microchipNumber,
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
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
                modifier = modifier
            )

            // сохранение
            Button(
                modifier = Modifier.padding(16.dp),
                enabled = nameIsCorrect && kindIsCorrect && breedIsCorrect &&
                        coatIsCorrect && colorIsCorrect && dateIsCorrect && microchipNumberIsCorrect,
                colors = ButtonDefaults.buttonColors(containerColor = GreenButton),
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
                Text(
                    text = stringResource(id = R.string.save_button_description),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ClearIcon(clear: () -> Unit) {
    IconButton(onClick = clear) {
        Icon(
            Icons.Default.Clear,
            contentDescription = stringResource(id = R.string.clear)
        )
    }
}
