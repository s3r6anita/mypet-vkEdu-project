package com.f4.mypet.ui.screens.medcard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.ui.screens.profile.show.TextComponent
import com.f4.mypet.ui.theme.BlueCheckbox
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.LightBlueBackground
import com.f4.mypet.ui.theme.RedButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TherapyScreen(navController: NavHostController, profileId: String?, therapyId: String?, scope: CoroutineScope) {

    var openAlertDialog by remember { mutableStateOf(false) }
    val dialogShape = RoundedCornerShape(12.dp)
    if (openAlertDialog) {
        AlertDialog(
            shape = dialogShape,
            title = {
                Text(stringResource(R.string.therapy_delete))
            },
            text = {
                Text(stringResource(R.string.therapy_question_to_delete))
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
                        scope.launch {
                            delay(100)
                            //TODO removeTherapy(profileId, therapyId)
                        }
                    }
                ) {
                    Text(stringResource(R.string.therapy_delete_button))
                }
            },
            dismissButton = {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(contentColor = BlueCheckbox),
                    onClick = {
                        openAlertDialog = false
                    }
                ) {
                    Text(stringResource(R.string.therapy_cancel_button))
                }
            },
            containerColor = Color.White,
            modifier = Modifier.shadow(elevation = 8.dp,shape = dialogShape)
        )
    }

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(R.string.therapy_title),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
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
                            // TODO: потом заменить строку из ресурсов на данные из БД
                            text = stringResource(R.string.therapy_tmp_name),
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(bottom = 20.dp),
                        )
                        TextComponent(
                            header = stringResource(R.string.therapy_type),
                            value = stringResource(R.string.therapy_tmp_type)
                        )
                        TextComponent(
                            header = stringResource(R.string.therapy_date),
                            value = stringResource(R.string.therapy_tmp_date)
                        )
                        Box(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = stringResource(R.string.therapy_tmp_notice),
                                onValueChange = { },
                                readOnly = true,
                                placeholder = {},
                                label = { Text("Заметки") },
                                shape = RoundedCornerShape(12.dp), // Радиус закругления рамки
                                modifier = Modifier
                                    .height(IntrinsicSize.Min)
                                    .fillMaxWidth()
                                    .padding(bottom = 15.dp)
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pet_icon),
                        contentDescription = stringResource(id = R.string.therapy_tmp_icon),
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
                    ,
                    onClick = {
                        openAlertDialog = true
                    },
                    border = BorderStroke(1.dp, RedButton),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = RedButton)
                ) {
                    Text(
                        text = stringResource(id = R.string.therapy_delete_button),
                        modifier = Modifier.padding(start = 10.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

        }
    }
}
//TODO suspen fun removeTherapy (profileId: String?, therapyId: String?)

