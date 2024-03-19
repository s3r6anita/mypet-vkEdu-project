package com.f4.mypet.ui.screens.profile.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.ui.CustomSnackBar
import com.f4.mypet.ui.MyPetTopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren

@Composable
fun ListProfileScreen(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
) {
    val (rememberUserChoice, onStateChange) = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(id = Routes.ListProfile.title),
                canNavigateBack = false,
                navigateUp = { },
                actions = {
                    // TODO: кнопка обратной связи
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ){
                CustomSnackBar(it.visuals.message)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // чек бокс "Запомнить мой выбор"
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 25.dp, end = 25.dp)
                    .toggleable(
                        value = rememberUserChoice,
                        onValueChange = { onStateChange(!rememberUserChoice) },
                        role = Role.Checkbox
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = rememberUserChoice, onCheckedChange = null
                )
                Text(
                    text = stringResource(R.string.remember_my_choice),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

//            список питомцев
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(vertical = 10.dp, horizontal = 25.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                @Suppress("MagicNumber") Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    listOf(1, 2, 3).forEachIndexed { index, pet ->
                        PetItem(
                            profileId = index,
                            canExit = rememberUserChoice,
                            navController = navController,
                            closeSnackbar = { scope.coroutineContext.cancelChildren() }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }

//            кнопка добавления нового питомца в список
            Button(
                modifier = Modifier.padding(20.dp),
                onClick = {
                    scope.coroutineContext.cancelChildren()
                    navController.navigate(Routes.CreateProfile.route) { launchSingleTop = true }
                }
            ) {
                Text(text = stringResource(id = R.string.add_button_description))
            }
        }
    }
}


@Composable
fun PetItem(
    profileId: Int,
    canExit: Boolean,
    navController: NavHostController,
    closeSnackbar: () -> Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            closeSnackbar()
            navController.navigate(Routes.Profile.route + "/" + profileId)
        }
    ) {
        Row(
            modifier = Modifier.padding(20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.pet_icon),
                contentDescription = stringResource(id = R.string.pet_photo_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .height(80.dp)
                    .width(80.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Питомец #$profileId",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
