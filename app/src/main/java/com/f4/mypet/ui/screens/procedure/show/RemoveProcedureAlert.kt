package com.f4.mypet.ui.screens.procedure.show

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.ui.theme.BlueCheckbox

@Composable
fun RemoveProcedureAlert(
    procedure: Procedure,
    navigateUp: () -> Unit,
    closeAlertDialog: () -> Unit,
    viewModel: ProcedureViewModel = hiltViewModel()
) {
    AlertDialog(
        shape = RoundedCornerShape(12.dp),
        title = {
            Text(text = stringResource(R.string.procedure_screen_delete_title_alert))
        },
        text = {
            Text(text = stringResource(R.string.procedure_screen_question_delete_procedure))
        },
        onDismissRequest = {
            closeAlertDialog()
        },
        confirmButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(contentColor = BlueCheckbox),
                onClick = {
                    closeAlertDialog()
                    navigateUp()
                    viewModel.deleteProcedure(procedure)
                }
            ) {
                Text(stringResource(R.string.procedure_screen_delete))
            }
        },
        dismissButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(contentColor = BlueCheckbox),
                onClick = {
                    closeAlertDialog()
                }
            ) {
                Text(stringResource(R.string.procedure_screen_cancel))
            }
        },
        containerColor = Color.White,
        modifier = Modifier.shadow(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp)
        )
    )
}
