package com.f4.mypet.ui.screens.profile

import android.content.Context
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.f4.mypet.R
import com.f4.mypet.ui.MyPetTopBar
import com.f4.mypet.ui.dateFormat
import java.util.Date

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CreateUpdateProfileScreen(context: Context) {
    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(R.string.create_profile_screen_title),
                canNavigateBack = true,
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
            // пол
            val radioOptions = listOf("Мужской", "Женский")
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

            Text(
                text = "Пол",
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
                                    onClick = { onOptionSelected(text) },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),

                            ) {
                            RadioButton(
                                selected = (text == selectedOption),
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
            OutlinedTextField(
                value = "some nickname",
                onValueChange = {  },
                label = { Text(stringResource(id = R.string.pet_nickname)) },
                trailingIcon = {
                    Icon(Icons.Default.Clear, contentDescription = stringResource(id = R.string.clear))
                }

            )

            // вид
            OutlinedTextField(
                value = "some view",
                onValueChange = {  },
                label = { Text(stringResource(id = R.string.pet_view)) },
                trailingIcon = {
                    Icon(Icons.Default.Clear, contentDescription = stringResource(id = R.string.clear))
                },
                modifier = Modifier.padding(top = 5.dp)
            )

            // порода
            OutlinedTextField(
                value = "some breed",
                onValueChange = {  },
                label = { Text(stringResource(id = R.string.pet_breed)) },
                trailingIcon = {
                    Icon(Icons.Default.Clear, contentDescription = stringResource(id = R.string.clear))
                },
                modifier = Modifier.padding(top = 5.dp)
            )

            // окрас
            OutlinedTextField(
                value = "some coat",
                onValueChange = {  },
                label = { Text(stringResource(id = R.string.pet_coat)) },
                trailingIcon = {
                    Icon(Icons.Default.Clear, contentDescription = stringResource(id = R.string.clear))
                },
                modifier = Modifier.padding(top = 5.dp)
            )

            // номер микрочипа
            OutlinedTextField(
                value = "some microchip",
                onValueChange = {  },
                label = { Text(stringResource(id = R.string.pet_microchip)) },
                trailingIcon = {
                    Icon(Icons.Default.Clear, contentDescription = stringResource(id = R.string.clear))
                },
                modifier = Modifier.padding(top = 5.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            // дата рождения
            var openDialog by remember { mutableStateOf(false) }
            var dateString by remember { mutableStateOf(dateFormat.format(Date())) }

            OutlinedTextField(
                value = dateString,
                onValueChange = {
                    if (it.length <= 10) dateString = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                label = { Text(stringResource(id = R.string.pet_birthday)) },
                supportingText = { Text(text = stringResource(id = R.string.date_format)) },
                trailingIcon = {
                    IconButton(onClick = { openDialog = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = stringResource(id = R.string.show_calendar))
                    }
                },
                modifier = Modifier.padding(top = 5.dp)
            )
            if (openDialog) {
                val datePickerState = rememberDatePickerState()
                DatePickerDialog(
                    onDismissRequest = {
                        openDialog = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openDialog = false
                                dateString = dateFormat.format(Date(
                                    datePickerState.selectedDateMillis ?: 0
                                ))
                            },
                        ) {
                            Text("ОК")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { openDialog = false }
                        ) {
                            Text("Отмена")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            // сохранение
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    try {
                        // TODO: Проверки полей
                        Toast.makeText(
                            context,
                            "Питомец успешно добавлен",
                            Toast.LENGTH_SHORT
                        ).show()

                        // TODO: Навигация в список профилей
                    }
                    catch (e: IllegalArgumentException) {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }
//                    catch (e: Exception) {
//                        Toast.makeText(context, "Ошибка", Toast.LENGTH_LONG).show()
//                    }
                }
            ) {
                Text(text = stringResource(id = R.string.save_button_description))
            }
        }
    }
}
