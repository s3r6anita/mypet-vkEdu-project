package com.f4.mypet.ui.screens.medcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.f4.mypet.PastOrPresentSelectableDates
import com.f4.mypet.PetDateTimeFormatter
import com.f4.mypet.R
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.OutlinedTextFieldColor
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTherapyScreen(
    navController: NavHostController,
    profileId: Int,
    isCreateScreen: Boolean
) {
    val isCreateScreen = true //TODO убрать
    val id = profileId?.toInt() ?: 0
    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(
                    if (isCreateScreen)
                        R.string.cu_therapy_title_create
                    else
                        R.string.cu_therapy_title_update
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
                .padding(horizontal = 30.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {

            val modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
            Box() {
                Column(
                    modifier = Modifier
                        //.padding(20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    var selectedName by remember { mutableStateOf("") }
                    OutlinedTextField(
                        modifier = modifier
                            .padding(bottom = 10.dp),
                        value = selectedName,
                        onValueChange = { selectedName = it },
                        label = {
                            if (isCreateScreen) Text(
                                "Название",
                                style = TextStyle(color = OutlinedTextFieldColor)
                            ) else {
                                Text(stringResource(R.string.cu_therapy_name)) //TODO подтягивание данных из БД
                            }
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    // дата выполнения
                    var openDialog by remember { mutableStateOf(false) }
                    val datePickerState =
                        rememberDatePickerState(selectableDates = PastOrPresentSelectableDates)
                    var dateIsCorrect by remember { mutableStateOf(true) }
                    var selectedDate by remember { mutableStateOf(LocalDateTime.now()) }
                    var dateIsChosen by remember { mutableStateOf(false) }
                    OutlinedTextField(
                        //TODO: отформатировать дату
                        value = if (dateIsChosen) selectedDate.format(PetDateTimeFormatter.date) else "",
                        onValueChange = {
                            selectedDate = LocalDateTime.parse(it, PetDateTimeFormatter.date)
                        },
                        label = {
                            if (isCreateScreen) Text(
                                "Дата",
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
                                        //TODO копирование в БД
                                        try {
                                            //TODO валидирование
                                        } catch (e: IllegalArgumentException) {
                                            //TODO введена неккоректная дата
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
                    // заметки
                    OutlinedTextField(
                        value = "",
                        onValueChange = { //TODO
                        },
                        label = {
                            if (isCreateScreen) Text(
                                stringResource(R.string.cu_therapy_tmp_notice),
                                style = TextStyle(color = OutlinedTextFieldColor)
                            ) else {
                                Text(stringResource(R.string.cu_therapy_tmp_notice)) //TODO подтягивание данных из БД
                            }
                        },
                        modifier = modifier
                            .height(120.dp),
                        shape = RoundedCornerShape(12.dp),
                    )
                }
            }


            // сохранение
            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Button(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .align(Alignment.BottomCenter),
                    onClick = {
                        // TODO: переход
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GreenButton)
                ) {
                    Text(
                        text = stringResource(R.string.save_button_description),
                        modifier = Modifier.padding(start = 10.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}