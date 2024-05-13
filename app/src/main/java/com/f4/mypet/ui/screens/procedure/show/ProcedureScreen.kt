package com.f4.mypet.ui.screens.procedure.show

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.PetDateTimeFormatter
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.components.TextComponent
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.LightBlueBackground
import com.f4.mypet.ui.theme.RedButton
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun ProcedureScreen(
    navController: NavHostController,
    procedureId: Int,
    viewModel: ProcedureViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getProcedure(procedureId)
        }
    }
    val procedure by viewModel.procedureUiState.collectAsState()
    val title = viewModel.title
    val type = viewModel.type
    var frequency = viewModel.frequency

    var openAlertDialog by remember { mutableStateOf(false) }

    if (openAlertDialog) {
        RemoveProcedureAlert(
            procedure = procedure,
            navigateUp = { navController.navigateUp() },
            closeAlertDialog = {
                openAlertDialog = !openAlertDialog
            }
        )
    }

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(R.string.procedure_screen_title),
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 50.dp)
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onSecondary,
                    ),
                    modifier = Modifier
                        .padding(top = 50.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .padding(top = 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = title.name,
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.weight(1f),
                            )
                            if (procedure.isDone == 1) {
                                Image(
                                    imageVector = Icons.Filled.CheckCircle,
                                    contentDescription = stringResource(id = R.string.procedure_screen_procedure_is_done)
                                )
                            } else {
                                if (procedure.dateDone < LocalDateTime.now()) {
                                    Image(
                                        imageVector = Icons.Filled.Clear,
                                        contentDescription = stringResource(id = R.string.procedure_screen_procedure_is_not_done)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        TextComponent(
                            header = stringResource(R.string.procedure_screen_type),
                            value = type.name
                        )
                        TextComponent(
                            header = stringResource(R.string.procedure_screen_date_of_event),
                            value = procedure.dateDone.format(PetDateTimeFormatter.date)
                        )
                        TextComponent(
                            header = stringResource(R.string.procedure_screen_time_of_event),
                            value = procedure.dateDone.format(PetDateTimeFormatter.time)
                        )
                        TextComponent(
                            header = stringResource(R.string.procedure_screen_frequency),
                            value = frequency.frequency
                        )
                        procedure.reminder?.format(PetDateTimeFormatter.dateTime)?.let {
                            TextComponent(
                                header = stringResource(R.string.procedure_screen_reminder),
                                value = it
                            )
                        }
                        TextComponent(
                            header = stringResource(R.string.procedure_screen_notice),
                            value = procedure.notes
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        // TODO: менять на иконку, соответствующую названию
                        painter = painterResource(id = R.drawable.procedures_icon),
                        contentDescription = stringResource(id = R.string.procedure_screen_icon_procedure_desc),
                        contentScale = ContentScale.Inside,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(LightBlueBackground)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
            ) {
                // кнопка редактирования
                Button(
                    contentPadding = PaddingValues(start = 1.dp, end = 1.dp),
                    border = BorderStroke(1.dp, GreenButton),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GreenButton),
                    onClick = {
                        navController.navigate("${Routes.UpdateProcedure.route}/$procedureId") {
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .padding(bottom = 40.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.edit_button_description),
                        modifier = Modifier.padding(start = 5.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.width(24.dp))

                // кнопка удаления
                Button(
                    modifier = Modifier
                        .padding(bottom = 40.dp)
                        .weight(1f),
                    onClick = {
                        openAlertDialog = true
                    },
                    border = BorderStroke(1.dp, RedButton),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = RedButton)
                ) {
                    Text(
                        text = stringResource(id = R.string.procedure_screen_delete),
                        modifier = Modifier.padding(start = 10.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

        }
    }
}
