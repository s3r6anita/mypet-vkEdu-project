package com.f4.mypet.ui.screens.procedure








import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.f4.mypet.ui.components.BottomBarData
import com.f4.mypet.ui.components.MyPetBottomBar
import com.f4.mypet.ui.components.MyPetSnackBar
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.LightBlueBackground

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.screens.profile.TextComponent
import com.f4.mypet.ui.theme.BlueCheckbox
import com.f4.mypet.ui.theme.RedButton

@Composable
fun ProcedureScreen(
    navController: NavHostController,
    profileId: Int,
    procedureId: Int
) {
    var openAlertDialog by remember { mutableStateOf(false) }
    val dialogShape = RoundedCornerShape(12.dp)
    if (openAlertDialog) {
        AlertDialog(
            shape = dialogShape,
            title = {
                Text(text = stringResource(R.string.procedure_screen_delete_title_alert))
            },
            text = {
                Text(text = stringResource(R.string.procedure_screen_question_delete_procedure))
            },
            onDismissRequest = {
                openAlertDialog = false
            },
            confirmButton = {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(contentColor = BlueCheckbox),
                    onClick = {
                        openAlertDialog = false
                        navController.navigateUp()
//                      // TODO: вставить вызов функции removeProcedure(id) внутри scope.launch { delay(100), ...}
                    }
                ) {
                    Text(stringResource(R.string.procedure_screen_delete))
                }
            },
            dismissButton = {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(contentColor = BlueCheckbox),
                    onClick = {
                        openAlertDialog = false
                    }
                ) {
                    Text(stringResource(R.string.procedure_screen_cancel))
                }
            },
            containerColor = Color.White,
            modifier = Modifier.shadow(elevation = 8.dp,shape = dialogShape)
        )
    }

    Scaffold(
        topBar = {
            MyPetTopBar(
                //TODO: поменять на text = stringResource(R.string.procedure_screen_title),
                text = "Процедура #$procedureId",
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
            // название
            Box(modifier = Modifier
                .padding(vertical = 50.dp)
            ) {
                Card (
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
                        Text(
                            // TODO: потом заменить строку из ресурсов на кличку (pet.nickname)
                            text = stringResource(R.string.procedure_screen_tmp_name),
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.weight(1f),
                        )
                        TextComponent(
                            header = stringResource(R.string.procedure_screen_type), value = "Вакцинация"
                        )
                        TextComponent(
                            header = stringResource(R.string.procedure_screen_date_of_event), value = stringResource(R.string.procedure_screen_tmp_date)
                        )
                        TextComponent(
                            header = stringResource(R.string.procedure_screen_time_of_event), value = stringResource(R.string.procedure_screen_tmp_time)
                        )
                        TextComponent(
                            header = stringResource(R.string.procedure_screen_place_of_event), value = stringResource(R.string.procedure_screen_tmp_place)
                        )
                        TextComponent(
                            header = stringResource(R.string.procedure_screen_reminder), value = stringResource(R.string.procedure_screen_tmp_time_for_proc)
                        )
                        TextComponent(
                            header = stringResource(R.string.procedure_screen_notice), value = stringResource(R.string.procedure_screen_tmp_notice)
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.some_procedure_icon),
                        contentDescription = stringResource(id = R.string.pet_photo_description),
                        contentScale = ContentScale.Inside,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(LightBlueBackground)
                    )
                }

            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
            ){
                Button(
                    modifier = Modifier
                        .padding(bottom = 40.dp)
                        .weight(1f),
                    contentPadding = PaddingValues(start = 1.dp, end = 1.dp),
                    onClick = {
                        //TODO сделать навагацию
                    },
                    border = BorderStroke(1.dp, GreenButton),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GreenButton)
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
                        .weight(1f)
                    , // Добавляем отступ 24 пикселя между кнопками,
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

//TODO removeProcedure
