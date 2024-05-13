package com.f4.mypet.ui.screens.profile.show

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.navigation.START
import com.f4.mypet.ui.components.BottomBarData
import com.f4.mypet.ui.components.MyPetBottomBar
import com.f4.mypet.ui.components.MyPetSnackBar
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.screens.profile.show.screencomponents.ProfileItem
import com.f4.mypet.ui.screens.profile.show.screencomponents.RemoveProfileALert
import com.f4.mypet.ui.theme.GreenButton
import kotlinx.coroutines.launch

@Composable
@Suppress("LongParameterList")
fun ProfileScreen(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    profileId: Int,
    canNavigateBack: Boolean,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getPetProfile(profileId)
        }
    }

    val pet by viewModel.petUiState.collectAsState()

    var openAlertDialog by remember { mutableStateOf(false) }

    if (openAlertDialog) {
        RemoveProfileALert(
            pet = pet,
            navController = navController,
            closeAlertDialog = {
                openAlertDialog = !openAlertDialog
            }
        )
    }

    Scaffold(
        topBar = {
            MyPetTopBar(text = stringResource(Routes.BottomBarRoutes.Profile.title),
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
                            popUpTo(if (canNavigateBack) Routes.ListProfile.route else START) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ExitToApp,
                            contentDescription = stringResource(id = R.string.exit_button_description)
                        )
                    }
                })
        },
        bottomBar = {
            MyPetBottomBar(
                profileId = profileId,
                canNavigateBack = canNavigateBack,
                items = BottomBarData.items,
                navController = navController
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) {
                MyPetSnackBar(text = it.visuals.message)
            }
        },

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
                ProfileItem(pet)
            }

            // кнопка редактирования
            Button(
                modifier = Modifier.padding(bottom = 40.dp),
                onClick = {
                    navController.navigate("${Routes.UpdateProfile.route}/$profileId") {
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
