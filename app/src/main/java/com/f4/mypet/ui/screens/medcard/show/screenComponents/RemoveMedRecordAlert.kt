package com.f4.mypet.ui.screens.medcard.show.screenComponents

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.MedRecord
import com.f4.mypet.ui.screens.medcard.show.MedRecordViewModel
import kotlinx.coroutines.launch


@Composable
fun RemoveMedRecordAlert(
    medRecord: MedRecord,
    closeAlertDialog: () -> Unit,
    viewModel: MedRecordViewModel = hiltViewModel()
){
    val scope = rememberCoroutineScope()

    AlertDialog(
        shape = RoundedCornerShape(12.dp),
        title = {
            Text(stringResource(R.string.therapy_delete))
        },
        text = {
            Text(stringResource(R.string.therapy_question_to_delete))
        },
        onDismissRequest = { closeAlertDialog() },
        confirmButton = {
            TextButton(
                onClick = {
                    closeAlertDialog()
                    scope.launch {
                        viewModel.deleteMedRecord(medRecord)
                    }
                }
            ) {
                Text(stringResource(R.string.therapy_delete_button))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    closeAlertDialog()
                }
            ) {
                Text(stringResource(R.string.therapy_cancel_button))
            }
        },
        modifier = Modifier.shadow(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp)
        )
    )
}
