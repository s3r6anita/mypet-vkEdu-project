package com.f4.mypet.ui.screens.profile.show.screencomponents

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.ui.screens.profile.show.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun RemoveProfileAlert(
    pet: Pet,
    closeAlertDialog: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

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
                scope.launch {
                    viewModel.removePet(pet)
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
