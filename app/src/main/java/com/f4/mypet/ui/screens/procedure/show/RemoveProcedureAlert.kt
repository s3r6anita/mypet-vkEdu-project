package com.f4.mypet.ui.screens.procedure.show

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.Procedure

@Composable
fun RemoveProcedureAlert(
    procedure: Procedure,
    closeAlertDialog: () -> Unit,
    viewModel: ProcedureViewModel = hiltViewModel()
) {
    AlertDialog(
        title = {
            Text(text = stringResource(R.string.procedure_screen_delete_title_alert))
        },
        text = {
            Text(text = stringResource(R.string.procedure_screen_question_delete_procedure))
        },
        onDismissRequest = { closeAlertDialog() },
        confirmButton = {
            TextButton(
                onClick = {
                    closeAlertDialog()
                    viewModel.deleteProcedure(procedure)
                }
            ) {
                Text(stringResource(R.string.procedure_screen_delete))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { closeAlertDialog() }
            ) {
                Text(stringResource(R.string.procedure_screen_cancel))
            }
        }
    )
}
