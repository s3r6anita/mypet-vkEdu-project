package com.f4.mypet.ui.screens.procedure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.ui.components.MyPetTopBar


@Composable
fun ProcedureScreen(
    navController: NavHostController,
    profileId: Int?,
    procedureId: Int?
) {
    var openAlertDialog by remember { mutableStateOf(false) }

    if (openAlertDialog) {
        AlertDialog(
            title = {
                Text(text = stringResource(R.string.procedure_screen_delete))
            },
            text = {
                Text(text = stringResource(R.string.procedure_screen_question_delete_procedure))
            },
            onDismissRequest = {
                openAlertDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openAlertDialog = false
                        navController.navigateUp()
//                      // TODO: вставить вызов функции removeProcedure(id) внутри scope.launch { delay(100), ...}
                    }
                ) {
                    Text(stringResource(R.string.procedure_screen_ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openAlertDialog = false
                    }
                ) {
                    Text(stringResource(R.string.procedure_screen_cancel))
                }
            }
        )
    }

    Scaffold(
        topBar = {
            MyPetTopBar(
                //TODO: поменять на text = stringResource(R.string.procedure_screen_title),
                text = "Процедура #$procedureId",
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() },
                actions = {
                    IconButton(onClick = {
                        openAlertDialog = true
                    }) {
                        Icon(
                            Icons.Filled.Delete,
                            stringResource(R.string.procedure_screen_delete_button)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.UpdateProcedure.route + "/" + profileId + "/" + procedureId) {
                        launchSingleTop = true
                    }
                },
            ) {
                Icon(Icons.Rounded.Edit, stringResource(R.string.procedure_screen_edit_procedure))
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            // название
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.procedure_screen_tmp_name),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.weight(1f)
                )
                if (true /*заглушка... TODO procedure.isDone */) {
                    Icon(
                        imageVector = Icons.Rounded.Done,
                        contentDescription = stringResource(R.string.procedure_screen_procedure_is_done)
                    )
                } else {
                    if (true /*заглушка... TODO procedure.dateDone < Date()*/) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = stringResource(R.string.procedure_screen_procedure_is_not_done)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.Info,
                            contentDescription = stringResource(R.string.procedure_screen_procedure_will_be_done)
                        )
                    }
                }
            }

            // дата и время выполенения
            Text(
                text = stringResource(R.string.procedure_screen_tmp_time),
                //TODO timeFormat.format(procedure.timeDone)
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(R.string.procedure_screen_tmp_date),
                //TODO text = dateFormat.format(procedure.dateDone)
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = stringResource(R.string.procedure_screen_reminder),
                style = MaterialTheme.typography.labelMedium,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
            )
            if (true /*заглушка... TODO procedure.settings.isReminderEnabled*/) {
                Text(
                    text = stringResource(R.string.procedure_screen_tmp_time_for_proc),
                    //TODO ${procedure.settings.beforeReminderTime}
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            } else {
                Text(
                    text = stringResource(R.string.procedure_screen_turned_off),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            OutlinedTextField(
                value = stringResource(R.string.procedure_screen_tmp_notice), //TODO procedure.notes
                onValueChange = { /*TODO procedure.notes*/ },
                readOnly = true,
                label = { Text(stringResource(R.string.procedure_screen_notice)) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )
        }
    }
}

//TODO removeProcedure
