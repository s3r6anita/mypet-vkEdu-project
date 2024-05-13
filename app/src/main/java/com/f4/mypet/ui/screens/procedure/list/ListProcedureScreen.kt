package com.f4.mypet.ui.screens.procedure.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.ui.components.BottomBarData
import com.f4.mypet.ui.components.MyPetBottomBar
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.components.PetCardHeader
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.LightGreenBackground
import kotlinx.coroutines.launch

@Composable
fun ListProcedureScreen(
    profileId: Int,
    canNavigateBack: Boolean,
    navController: NavHostController,
    viewModel: ListProcedureViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getPetsProcedures(profileId)
        }
        scope.launch {
            viewModel.getTitles()
        }
    }

    val procedures by viewModel.proceduresUiState.collectAsState()
    val pet = viewModel.pet
    val titles by viewModel.titlesUiState.collectAsState()

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(id = R.string.list_procedure_screen_title),
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() },
                actions = { }
            )
        },
        bottomBar = {
            MyPetBottomBar(
                profileId = profileId,
                canNavigateBack = canNavigateBack,
                items = BottomBarData.items,
                navController = navController
            )
        },
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
        ) {
            PetCardHeader(petName = pet.name, backgroundColor = LightGreenBackground)

            // список процедур
            Column(
                modifier = Modifier
                    .height(400.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                procedures.forEach { procedure ->
                    ProcedureItem(
                        procedure = procedure,
                        navController = navController,
                        title = titles.find { title -> title.id == procedure.title }?.name
                            ?: stringResource(id = R.string.unknown)
                    )
                }
            }

            // кнопка ADD
            Button(
                modifier = Modifier.padding(vertical = 20.dp),
                onClick = {
                    navController.navigate(Routes.CreateProcedure.route + "/" + profileId) {
                        launchSingleTop = true
                    }
                },
                border = BorderStroke(1.dp, GreenButton),
                colors = ButtonDefaults.buttonColors(containerColor = GreenButton)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_button_icon_description)
                )
                Text(
                    text = stringResource(id = R.string.add_button_description),
                    Modifier.padding(start = 10.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
