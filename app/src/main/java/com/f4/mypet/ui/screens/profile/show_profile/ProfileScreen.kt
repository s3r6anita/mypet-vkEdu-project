package com.f4.mypet.ui.screens.profile.show_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.PetDateTimeFormatter
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.navigation.START
import com.f4.mypet.ui.CustomSnackBar
import com.f4.mypet.ui.MyPetTopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    profileId: Int?,
    scope: CoroutineScope
) {

    val viewModel = hiltViewModel<ProfileViewModel>()
    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getPetProfile(profileId)
        }
    }

    val pet by viewModel.petUiState.collectAsState()

    var openAlertDialog by remember { mutableStateOf(false) }

    if (openAlertDialog) {
        AlertDialog(title = {
            Text(text = stringResource(id = R.string.profile_screen_delete_pet_title))
        }, text = {
            Text(text = stringResource(id = R.string.profile_screen_delete_pet_text))
        }, onDismissRequest = {
            openAlertDialog = false
        }, confirmButton = {
            TextButton(onClick = {
                openAlertDialog = false
                navController.navigate(START) {
                    popUpTo(Routes.ListProfile.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
                //TODO: вставить вызов функции removePet(id) внутри scope.launch { delay(100), ...}
            }) {
                Text("ОК")
            }
        }, dismissButton = {
            TextButton(onClick = {
                openAlertDialog = false
            }) {
                Text("Отмена")
            }
        })
    }

    Scaffold(
        topBar = {
            MyPetTopBar(text = stringResource(Routes.Profile.title),
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() },
                actions = {
                    // кнопка удалить
                    IconButton(onClick = {
                        openAlertDialog = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.delete_button_description)
                        )
                    }

                    // кнопка поделиться
                    IconButton(onClick = {
                        // TODO: реализовать кнопку поделиться
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = stringResource(id = R.string.share_button_description)
                        )
                    }

                    // кнопка выхода
                    IconButton(onClick = {
                        navController.navigate(START) {
                            popUpTo(Routes.ListProfile.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = stringResource(id = R.string.exit_button_description)
                        )
                    }
                })
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ){
                CustomSnackBar(it.visuals.message)
            }
        },

//        редактирование профиля
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.UpdateProfile.route + "/" + profileId) {
                        launchSingleTop = true
                    }
                }, modifier = Modifier
            ) {
                Icon(
                    Icons.Rounded.Edit,
                    stringResource(id = R.string.update_profile_button_description)
                )
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
                text = stringResource(R.string.information_about_pet) + " #$profileId",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 20.dp),
            )
            TextComponent(
                header = stringResource(R.string.pet_nickname), value = pet.name
            )
            TextComponent(
                header = stringResource(R.string.pet_view), value = pet.kind
            )
            TextComponent(
                header = stringResource(R.string.pet_breed), value = pet.breed
            )
            TextComponent(
                header = stringResource(R.string.pet_sex), value = pet.sex
            )
            TextComponent(
                header = stringResource(R.string.pet_birthday),
                value = pet.birthday.format(PetDateTimeFormatter.date)
            )
            TextComponent(
                header = stringResource(R.string.pet_coat), value = pet.coat
            )
            TextComponent(
                header = stringResource(R.string.pet_color), value = pet.color
            )
            TextComponent(
                header = stringResource(R.string.pet_microchip), value = pet.microchipNumber
            )
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


// TODO: function formatPet(pet: Pet): String

// TODO: function removePet(id: Int)
