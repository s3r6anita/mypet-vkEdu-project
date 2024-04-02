package com.f4.mypet.ui.screens.procedure.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.PetDateTimeFormatter
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.navigation.Routes
import com.f4.mypet.ui.components.BottomBarData
import com.f4.mypet.ui.components.MyPetBottomBar
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.screens.medcard.PetCardHeader
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.LightBlueBackground
import com.f4.mypet.ui.theme.LightGreenBackground
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
                text = stringResource(id = R.string.list_procedure_screen_title),
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() },
                actions = {}
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Карточка питомца = заголовок = то что сверху
            PetCardHeader(petName = pet.name, backgroundColor = LightGreenBackground)

            // список процедур
            @Suppress("MagicNumber") Column(
                modifier = Modifier
                    .height(400.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                procedures.forEach { procedure ->
                    ProcedureItem(
                        procedure = procedure,
                        titles = titles,
                        profileId = profileId,
                        navController = navController
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

@Composable
fun ProcedureItem(
    procedure: Procedure,
    titles: List<ProcedureTitle>,
    profileId: Int?,
    navController: NavHostController
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
        ),
        modifier = Modifier
            .padding(bottom = 15.dp)
            .clickable {
                navController.navigate(Routes.Procedure.route + "/" + profileId + "/" + procedure.id) {
                    launchSingleTop = true
                }
            }
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 15.dp, horizontal = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(id = R.drawable.procedures_icon),
                    contentDescription = stringResource(id = R.string.list_procedure_screen_title),
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(LightBlueBackground)
                        .size(50.dp),
                )
                Column (
                    modifier = Modifier
                        .padding(start = 20.dp),
                    verticalArrangement = Arrangement.Center
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = titles.find { title -> title.id == procedure.title }?.name ?: stringResource(
                                id = R.string.unknown),
                            style = MaterialTheme.typography.titleLarge
                        )
                        if (procedure.isDone == 1) {
                            Icon(
                                Icons.Rounded.Done,
                                contentDescription = null,
                                modifier = Modifier.padding(start = 10.dp),
                                tint = LightBlueBackground
                            )
                        }
                    }
                    Text(
                        text = procedure.dateCreated.format(PetDateTimeFormatter.dateTime),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(id = R.string.arrow_right_description),
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}
