package com.f4.mypet.ui.screens.medcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.navigation.START
import com.f4.mypet.ui.components.BottomBarData
import com.f4.mypet.ui.components.MyPetBottomBar
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.LightBlueBackground
import com.f4.mypet.ui.theme.LightGrayTint
import com.f4.mypet.ui.theme.LightGreenBackground

@Composable
fun MedCardScreen(
    navController: NavHostController,
    profileId: Int,
    canNavigateBack: Boolean
) {
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
                navController = navController,
                profileId = profileId,
                canNavigateBack = canNavigateBack,
                items = BottomBarData.items
            )
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
            // Карточка питомца = заголовок = то что сверху
            PetCardHeader(profileId = profileId)

            // Список мед мероприятий
            Column (
                modifier = Modifier
                    .height(400.dp)
                    .verticalScroll(rememberScrollState())
            ){
                //TODO: заменить на список мед.процедур
                listOf(1, 2, 3).forEach { index ->
                    MedItem(
                        //TODO: передавать ID MedRecord и др по аналогии с процедурами
                    )
                }
            }

            // кнопка добавления
            Button(
                modifier = Modifier.padding(bottom = 20.dp),
                onClick = {
                    // TODO: переход на добавление мед.процедуры
                },
                colors = ButtonDefaults.buttonColors(containerColor = GreenButton)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_button_icon_description)
                )
                Text(
                    text = stringResource(id = R.string.add_button_description),
                    modifier = Modifier.padding(start = 10.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun PetCardHeader(
    profileId: Int
){
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = LightGreenBackground,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.pet_icon),
                contentDescription = stringResource(id = R.string.pet_photo_description),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.onSecondary),
            )
            Text(
                text = "Питомец #$profileId", //TODO: заменить на {pet.name}, т.е. кличку
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
    }
}

@Composable
fun MedItem(
    //TODO: передавать мед.процедуру (id)
){
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
        ),
        modifier = Modifier
            .padding(bottom = 15.dp)
            .clickable { }
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(){
                Image(
                    painter = painterResource(id = R.drawable.therapy_icon),
                    contentDescription = stringResource(id = R.string.pet_photo_description),
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(LightBlueBackground)
                        .size(50.dp),
                )
                Column (
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .height(50.dp),
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "Вакцинация", // TODO: заменить на medrecord.title
                        color = Color.Black,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "26.03.2024", // TODO: заменить на medrecord.date
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(id = R.string.arrow_right_description),
                modifier = Modifier.height(50.dp),
                tint = LightGrayTint
            )
        }
    }
}