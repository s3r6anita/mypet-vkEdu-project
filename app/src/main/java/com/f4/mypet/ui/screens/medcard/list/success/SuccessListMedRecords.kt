package com.f4.mypet.ui.screens.medcard.list.success

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.f4.mypet.ui.screens.medcard.list.ListMedRecordsViewModel
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.LightBlueBackground

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SuccessListMedRecords(
    canNavigateBack: Boolean,
    profileId: Int,
    getNavController: () -> NavHostController,
    viewModel: ListMedRecordsViewModel = hiltViewModel()
) {
    val navController = getNavController()

    val medRecords by viewModel.medRecordsUiState.collectAsState()
    val pet = viewModel.pet

    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refreshMedRecords(profileId) }
    )

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(id = R.string.medcard_screen_title),
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
                getNavController = { navController }
            )
        }
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
                PetCardHeader(petName = pet.name, backgroundColor = LightBlueBackground)

                // список медзаписей
                Column(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    medRecords.forEach { medRecord ->
                        MedRecordItem(
                            medRecord = medRecord,
                            navController = navController
                        )
                    }
                    Spacer(modifier = Modifier.height(50.dp)) // для нормального скролла
                }
            }

            // кнопка ADD
            ButtonComponent(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(innerPadding.calculateBottomPadding()),
                onClick = {
                    navController.navigate("${Routes.CreateMedRecord.route}/$profileId") {
                        launchSingleTop = true
                    }
                },
                text = stringResource(id = R.string.add_button_description),
                color = ButtonDefaults.buttonColors(containerColor = GreenButton),
                icon = Icons.Default.Add,
                textColor = Color.White,
                borderColor = GreenButton
            )

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
