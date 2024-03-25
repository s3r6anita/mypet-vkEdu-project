package com.f4.mypet.ui.screens.procedure

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.dateFormat
import com.f4.mypet.navigation.Routes
import com.f4.mypet.navigation.START
import com.f4.mypet.timeFormat
import com.f4.mypet.ui.components.BottomBarData
import com.f4.mypet.ui.components.MyPetBottomBar
import com.f4.mypet.ui.components.MyPetTopBar
import java.util.Date

@Composable
fun ListProcedureScreen(
    navController: NavHostController,
    profileId: Int?,
    canNavigateBack: Boolean?
) {
//    val pet = pets[profileId]
//    val procedures = pet.procedures

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = "Питомец #$profileId", // TODO: заменить на {pet.name}, т.е. кличку
                canNavigateBack = canNavigateBack ?: true,
                navigateUp = { navController.navigateUp() },
                actions = {
                    // кнопка выхода
                    IconButton(onClick = {
                        navController.navigate(START) {
                            popUpTo(Routes.ListProfile.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = stringResource(id = R.string.exit_button_description)
                        )
                    }
                }
            )
        },
        bottomBar = {
            MyPetBottomBar(
                navController = navController,
                profileId = profileId ?: 0,
                canNavigateBack = canNavigateBack ?: true,
                items = BottomBarData.items
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.CreateProcedure.route + "/" + profileId) {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
            ) {
                Icon(
                    Icons.Rounded.Add,
                    stringResource(id = R.string.list_procedure_screen_add_procedure)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
//                .height(800.dp)
                .padding(innerPadding)
        ) {
//            список процедур
            @Suppress("MagicNumber") Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                listOf(1, 2, 3).forEach { index ->
                    ProcedureItem(
                        procedureId = index, // TODO: заменить на {procedure.id}
                        profileId = profileId,
                        navController = navController
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun ProcedureItem(
    procedureId: Int,
    profileId: Int?,
    navController: NavHostController
) {
    ListItem( // TODO: поменять на данные, полученные из ВМ
        headlineContent = {
            Text(text = "Название")
        },
        supportingContent = {
            Text(text = "${timeFormat.format(Date())}\n${dateFormat.format(Date())}")
        },
        trailingContent = {
            if (true) {
                Icon(Icons.Rounded.Done, contentDescription = null)
            }
        },
        modifier = Modifier
            .clickable {
                navController.navigate(Routes.Procedure.route + "/" + profileId + "/" + procedureId) {
                    launchSingleTop = true
                }
            }
    )
}
