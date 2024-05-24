package com.f4.mypet.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.f4.mypet.R

@Composable
fun StatusDialog(
    msg: String?,
    close: () -> Unit
) {
    AlertDialog(
        text = { Text(text = msg ?: stringResource(R.string.error)) },
        onDismissRequest = { close() },
        confirmButton = {
            TextButton(onClick = {
                close()
            }) {
                Text(text = stringResource(id = R.string.confirm_button_description))
            }
        }
    )
}