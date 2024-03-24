package com.f4.mypet.ui.screens.profile

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.ColorFilter
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
import com.f4.mypet.ui.theme.BlueCheckbox
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.LightBlueBackground
import com.f4.mypet.ui.theme.LightGrayTint
import com.f4.mypet.ui.theme.White
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren

@Composable
fun ListProfileScreen(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
) {
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
            ) {
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
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(650.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//              чек бокс "Запомнить мой выбор"
                val (rememberUserChoice, onStateChange) = remember { mutableStateOf(false) }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp)
                        .toggleable(
                            value = rememberUserChoice,
                            onValueChange = { onStateChange(!rememberUserChoice) },
                            role = Role.Checkbox
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Checkbox(
                        checked = rememberUserChoice, onCheckedChange = null,
                        modifier = Modifier.padding(15.dp),
                        colors = CheckboxDefaults.colors(
                            checkedColor = BlueCheckbox,
                            uncheckedColor = LightGrayTint,
                            checkmarkColor = White
                        )
                    )
                    Text(
                        text = stringResource(R.string.remember_my_choice),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

//            список питомцев
                @Suppress("MagicNumber") Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    listOf(1, 2, 3).forEachIndexed { index, pet ->
                        PetItem(
                            profileId = index, // TODO: заменить на {pet.id}
                            canNavigateBack = !rememberUserChoice,
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
                },
                colors = ButtonDefaults.buttonColors(containerColor = GreenButton)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.list_profile_screen_add_button_icon_description)
                )
                Text(
                    text = stringResource(id = R.string.add_button_description),
                    Modifier.padding(start = 10.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PetItem(
    profileId: Int,
    canNavigateBack: Boolean,
    navController: NavHostController,
    closeSnackbar: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                closeSnackbar()
                navController.navigate(Routes.ListProcedure.route + "/" + profileId + "/" + canNavigateBack) {
                    launchSingleTop = true
                    if (!canNavigateBack) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.pet_icon),
                    contentDescription = stringResource(id = R.string.pet_photo_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .height(80.dp)
                        .width(80.dp),
                    colorFilter = ColorFilter.tint(LightBlueBackground)
                )
                Text(
                    text = "Питомец #$profileId",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(id = R.string.delete_button_description),
                modifier = Modifier.height(80.dp),
                tint = LightGrayTint
            )
        }
    }
}
