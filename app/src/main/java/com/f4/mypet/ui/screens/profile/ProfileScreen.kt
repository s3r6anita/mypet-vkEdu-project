package com.f4.mypet.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.navigation.START
import com.f4.mypet.ui.components.BottomBarData
import com.f4.mypet.ui.components.MyPetBottomBar
import com.f4.mypet.ui.components.MyPetSnackBar
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.LightBlueBackground

@Composable
fun ProfileScreen(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    profileId: Int,
    canNavigateBack: Boolean
) {

    var openAlertDialog by remember { mutableStateOf(false) }

    if (openAlertDialog) {
        AlertDialog(
            title = {
                Text(text = stringResource(id = R.string.profile_screen_delete_pet_title))
            },
            text = {
                Text(text = stringResource(id = R.string.profile_screen_delete_pet_text))
            },
            onDismissRequest = {
                openAlertDialog = false
            },
            confirmButton = {
                TextButton(onClick = {
                    openAlertDialog = false
                    navController.navigate(START) {
                        popUpTo(Routes.ListProfile.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                    // TODO: вставить вызов функции removePet(id) внутри scope.launch { delay(100), ...}
                }) {
                    Text(stringResource(id = R.string.profile_screen_delete_dialog_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { openAlertDialog = false }) {
                    Text(stringResource(id = R.string.profile_screen_delete_dialog_delete))
                }
            }
        )
    }

    Scaffold(
        topBar = {
            MyPetTopBar(text = stringResource(Routes.BottomBarRoutes.Profile.title),
                canNavigateBack = canNavigateBack,
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
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = stringResource(id = R.string.exit_button_description)
                        )
                    }
                }
            )
        },
        bottomBar = {
            MyPetBottomBar(
                navController = navController,
                profileId = profileId,
                canNavigateBack = canNavigateBack,
                items = BottomBarData.items
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
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 50.dp)
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onSecondary,
                    ),
                    modifier = Modifier
                        .padding(top = 50.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .padding(top = 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            // TODO: потом заменить строку из ресурсов на кличку (pet.nickname)
                            text = stringResource(R.string.pet) + "#$profileId",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(bottom = 20.dp),
                        )
                        TextComponent(
                            header = stringResource(R.string.pet_name), value = "some nickname"
                        )
                        TextComponent(
                            header = stringResource(R.string.pet_view), value = "some type"
                        )
                        TextComponent(
                            header = stringResource(R.string.pet_breed), value = "some breed"
                        )
                        TextComponent(
                            header = stringResource(R.string.pet_sex), value = "some paul"
                        )
                        TextComponent(
                            header = stringResource(R.string.pet_birthday),
                            value = "some birth date"
                        )
                        TextComponent(
                            header = stringResource(R.string.pet_coat), value = "some coat"
                        )
                        TextComponent(
                            header = stringResource(R.string.pet_color), value = "some color"
                        )
                        TextComponent(
                            header = stringResource(R.string.pet_microchip),
                            value = "some microchip number"
                        )
                        // TODO: value для каждого TextComponent (example: value = pet.nickname)
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pet_icon),
                        contentDescription = stringResource(id = R.string.pet_photo_description),
                        contentScale = ContentScale.Inside,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(LightBlueBackground)
                    )
                }

            }
            // TODO: value для каждого TextComponent (example: value = pet.nickname)

            // кнопка редактирования
            Button(
                modifier = Modifier.padding(bottom = 40.dp),
                onClick = {
                    navController.navigate(Routes.UpdateProfile.route + "/" + profileId) {
                        launchSingleTop = true
                    }
                },
                border = BorderStroke(1.dp, GreenButton),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = GreenButton)
            ) {
                Icon(
                    Icons.Rounded.Edit,
                    stringResource(id = R.string.update_profile_button_description)
                )
                Text(
                    text = stringResource(id = R.string.edit_button_description),
                    modifier = Modifier.padding(start = 10.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun TextComponent(header: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = header,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(end = 10.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Right
        )
    }
}


// TODO: function formatPet(pet: Pet): String

// TODO: function removePet(id: Int)
