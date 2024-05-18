package com.f4.mypet.ui.screens.profile.show.screencomponents

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.navigation.Routes
import com.f4.mypet.navigation.START
import com.f4.mypet.ui.screens.profile.show.ProfileViewModel

@Composable
fun RemoveProfileALert(
    pet: Pet,
    getNavController: () ->  NavHostController,
    closeAlertDialog: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val navController = getNavController()
    val msg by viewModel.msg.collectAsState()
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.profile_screen_delete_pet_title))
        },
        text = {
            Text(text = stringResource(id = R.string.profile_screen_delete_pet_text))
        },
        onDismissRequest = {
            closeAlertDialog()
        },
        confirmButton = {
            TextButton(onClick = {
                closeAlertDialog()
                viewModel.removePet(pet)
                if (msg == null) {
                    navController.navigate(START) {
                        popUpTo(Routes.ListProfile.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }) {
                Text(text = stringResource(id = R.string.confirm_button_description))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                closeAlertDialog()
            }) {
                Text(text = stringResource(id = R.string.cancel_button_description))
            }
        }
    )
}
