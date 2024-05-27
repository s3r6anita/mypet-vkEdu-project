package com.f4.mypet.ui.screens.medcard.show.screenComponents

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.f4.mypet.R
import com.f4.mypet.ui.theme.BlueCheckbox


@Composable
fun RemoveMedRecordAlert(
    navigateUp: () -> Unit,
    closeAlertDialog: () -> Unit
){
    AlertDialog(
        shape = RoundedCornerShape(12.dp),
        title = {
            Text(stringResource(R.string.therapy_delete))
        },
        text = {
            Text(stringResource(R.string.therapy_question_to_delete))
        },
        onDismissRequest = {
            closeAlertDialog()
        },
        confirmButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(contentColor = BlueCheckbox),
                onClick = {
                    closeAlertDialog()
//                    scope.launch {
//                        //TODO removeMedRecord(medRecordId)
//                    }
                    navigateUp()
                }
            ) {
                Text(stringResource(R.string.therapy_delete_button))
            }
        },
        dismissButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(contentColor = BlueCheckbox),
                onClick = {
                    closeAlertDialog()
                }
            ) {
                Text(stringResource(R.string.therapy_cancel_button))
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier.shadow(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp)
        )
    )
}
