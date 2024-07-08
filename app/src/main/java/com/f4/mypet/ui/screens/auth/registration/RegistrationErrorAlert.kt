package com.f4.mypet.ui.screens.auth.registration

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.f4.mypet.R

@Composable
fun RegistrationErrorAlert(
    msg: String?,
    closeAlert: () -> Unit,
    retryAction: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(stringResource(id = R.string.error))
        },
        text = {
            Text(msg ?: stringResource(id = R.string.error_password_equality))
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
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    )
}
