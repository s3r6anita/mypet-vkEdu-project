package com.f4.mypet.ui.screens.profile

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.f4.mypet.R
import kotlinx.coroutines.InternalCoroutinesApi
import com.f4.mypet.ui.MyPetTopBar

@Composable
fun ProfileScreen() {

    //TODO id = profileId
    var openAlertDialog by remember { mutableStateOf(false) }
    //val context = LocalContext.current
    if (openAlertDialog) {
        AlertDialog(
            title = {
                Text(text = "Удаление профиля")
            },
            text = {
                Text(text = "Вы уверены, что хотите удалить профиль?")
            },
            onDismissRequest = {
                openAlertDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openAlertDialog = false
                        // TODO navController
                        //TODO вставить вызов функции removePet(id) внутри scope.launch { delay(100), ...}
                    }
                ) {
                    Text("ОК")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openAlertDialog = false
                    }
                ) {
                    Text("Отмена")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(R.string.profile),
                canNavigateBack = true,
                actions = {
                    // кнопка удалить
                    IconButton(onClick = {
                        openAlertDialog = true
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Удалить"
                        )
                    }
                    // кнопка поделиться
                    IconButton(onClick = {
                       //TODO кнопка поделиться
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Поделиться"
                        )
                    }
                    // кнопка выхода
                    IconButton(onClick = {
                        // TODO navController
                    }
                    ) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "Выход"
                        )
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                        // TODO navController in onClick
                },
                modifier = Modifier
            ) {
                Icon(Icons.Rounded.Edit, "Изменить профиль питомца")
            }
        },

        floatingActionButtonPosition = FabPosition.End

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.information_about_pet),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 20.dp),
            )
            TextComponent(
                header = stringResource(R.string.pet_nickname),
                value = "some nickname"
            )
            TextComponent(
                header = stringResource(R.string.pet_view),
                value = "some type"
            )
            TextComponent(
                header = stringResource(R.string.pet_breed),
                value = "some breed"
            )
            TextComponent(
                header = stringResource(R.string.pet_paul),
                value = "some paul"
            )
            TextComponent(
                header = stringResource(R.string.pet_birthday),
                value = "some birth date"
            )
            TextComponent(
                header = stringResource(R.string.pet_coat),
                value = "some coat"
            )
            TextComponent(
                header = stringResource(R.string.pet_microchip),
                value = "some microchip number"
            )
            // TODO value для каждого TextComponent (example: value = pet.nickname)
        }
    }
}

@Composable
fun TextComponent(header: String, value: String) {
    Text(
        text = header,
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(bottom = 4.dp),
    )
    Text(
        text = value,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}


//TODO function formatPet(pet: Pet): String

//TODO function removePet(id: Int)