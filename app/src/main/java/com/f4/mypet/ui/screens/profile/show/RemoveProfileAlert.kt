package com.f4.mypet.ui.screens.profile.show

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavOptionsBuilder
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.navigation.Routes
import com.f4.mypet.navigation.START

@Composable
fun RemoveProfileALert(
    pet: Pet,
    viewModel: ProfileViewModel = hiltViewModel(),
    navigate: (String, NavOptionsBuilder.() -> Unit) -> Unit,
    closeAlertDialog: () -> Unit
) {
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
                navigate(START) {
                    popUpTo(Routes.ListProfile.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
                viewModel.removePet(pet)
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
