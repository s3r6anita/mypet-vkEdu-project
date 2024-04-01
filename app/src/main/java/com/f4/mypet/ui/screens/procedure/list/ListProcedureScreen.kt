package com.f4.mypet.ui.screens.procedure.list

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.PetDateTimeFormatter
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.navigation.Routes
import com.f4.mypet.navigation.START
import com.f4.mypet.ui.components.BottomBarData
import com.f4.mypet.ui.components.MyPetBottomBar
import com.f4.mypet.ui.components.MyPetTopBar
import kotlinx.coroutines.launch

@Composable
fun ListProcedureScreen(
    navController: NavHostController,
    profileId: Int,
    canNavigateBack: Boolean
) {
    val scope = rememberCoroutineScope()
    val viewModel: ListProcedureViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getPetsProcedures(profileId)
        }
    }

    val procedures by viewModel.proceduresUiState.collectAsState()

    val titles = viewModel.titles
    val pet = viewModel.pet

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = pet.name,
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
                profileId = profileId,
                canNavigateBack = canNavigateBack,
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
                procedures.forEach { procedure ->
                    ProcedureItem(
                        procedure = procedure,
                        titles = titles,
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
    procedure: Procedure,
    titles: List<ProcedureTitle>,
    profileId: Int?,
    navController: NavHostController
) {
    ListItem(
        headlineContent = {
            Text(
                text = titles.find { title -> title.id == procedure.title }?.name ?: "Неизвестно",
                fontSize = 20.sp
            )
        },
        supportingContent = {
            Text(text = procedure.dateCreated.format(PetDateTimeFormatter.dateTime))
        },
        trailingContent = {
            if (procedure.isDone == 1) {
                Icon(Icons.Rounded.Done, contentDescription = null)
            }
        },
        modifier = Modifier
            .clickable {
                navController.navigate(Routes.Procedure.route + "/" + profileId + "/" + procedure.id) {
                    launchSingleTop = true
                }
            }
    )
}
