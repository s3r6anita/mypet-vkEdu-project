package com.f4.mypet.ui.screens.procedure.show

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import com.f4.mypet.data.db.entities.Procedure
import kotlinx.coroutines.launch

@Composable
fun RemoveProcedureAlert(
    procedure: Procedure,
    closeAlertDialog: () -> Unit,
    viewModel: ProcedureViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    AlertDialog(
        shape = RoundedCornerShape(12.dp),
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
                    scope.launch {
                        viewModel.deleteProcedure(procedure)
                    }
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
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier.shadow(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp)
        )
    )
}
