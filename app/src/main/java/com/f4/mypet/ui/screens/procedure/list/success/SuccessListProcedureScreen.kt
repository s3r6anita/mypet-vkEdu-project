package com.f4.mypet.ui.screens.procedure.list.success

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.ui.components.BottomBarData
import com.f4.mypet.ui.components.ButtonComponent
import com.f4.mypet.ui.components.MyPetBottomBar
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.components.PetCardHeader
import com.f4.mypet.ui.screens.procedure.list.ListProcedureViewModel
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.LightGreenBackground

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SuccessListProcedureScreen(
    canNavigateBack: Boolean,
    profileId: Int,
    getNavController: () -> NavHostController,
    viewModel: ListProcedureViewModel = hiltViewModel()
) {
    val navController = getNavController()

    val procedures by viewModel.proceduresUiState.collectAsState()
    val pet = viewModel.pet
    val titles by viewModel.titlesUiState.collectAsState()

    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refreshProcedures(profileId) }
    )

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(id = R.string.list_procedure_screen_title),
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            MyPetBottomBar(
                profileId = profileId,
                canNavigateBack = canNavigateBack,
                items = BottomBarData.items,
                getNavController = { navController }
            )
        },
    ) { innerPadding ->

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .pullRefresh(pullRefreshState)
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp),
            ) {
                PetCardHeader(petName = pet.name, backgroundColor = LightGreenBackground)

                // список процедур
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
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
                    Spacer(modifier = Modifier.height(50.dp)) // для нормального скролла
                }
            }

            // кнопка ADD
            ButtonComponent(
                onClick = {
                    navController.navigate("${Routes.CreateProcedure.route}/$profileId") {
                        launchSingleTop = true
                    }
                },
                text = stringResource(id = R.string.add_button_description),
                color = ButtonDefaults.buttonColors(containerColor = GreenButton),
                icon = Icons.Default.Add,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(innerPadding.calculateBottomPadding()),
                textColor = Color.White,
                borderColor = GreenButton,
                enabled = true
            )

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
