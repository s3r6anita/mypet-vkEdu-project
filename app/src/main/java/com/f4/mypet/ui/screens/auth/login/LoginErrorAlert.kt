package com.f4.mypet.ui.screens.auth.login

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.navigation.START

@Composable
fun LoginErrorAlert(
    msg: String?,
    closeAlert: () -> Unit,
    retryAction: () -> Unit,
    getNavController: () -> NavHostController
) {
    val navController = getNavController()

    AlertDialog(
        title = {
            Text(stringResource(id = R.string.error))
        },
        text = {
            Text(msg ?: stringResource(id = R.string.login_error))
        },
        onDismissRequest = { closeAlert() },
        confirmButton = {
            TextButton(
                onClick = {
                    closeAlert()
                    retryAction()
                }
            ) {
                Text(stringResource(id = R.string.login_retry))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                closeAlert()
                navController.navigate(Routes.ListProfile.route) {
                    popUpTo(START)
                    restoreState = true
                    launchSingleTop = true
                }
            }) {
                Text(stringResource(id = R.string.login_offline_button))
            }
        }

    )
}
