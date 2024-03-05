package com.f4.mypet.ui.screens.profile

import android.content.Context
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
import com.f4.mypet.R
import com.f4.mypet.ui.MyPetTopBar

@Composable
fun ListProfileScreen(context: Context){

    val (rememberUserChoice, onStateChange) = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = "",
                canNavigateBack = false,
                actions = {
                    //Здесь предполагалась кнопка обратной связи
                }
            )
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
            //Заголовок
            Row(
                modifier = Modifier
                    .height(90.dp)
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(R.string.my_pets_screen_title),
                    color = Color.Black, fontSize = 30.sp, fontWeight = FontWeight.Bold,)
            }

            // чек бокс "Запомнить мой выбор"
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 25.dp, end = 25.dp)
                    .toggleable(
                        value = rememberUserChoice,
                        onValueChange = { onStateChange(!rememberUserChoice) },
                        role = Role.Checkbox
                    )
                        ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = rememberUserChoice,
                    onCheckedChange = null
                )
                Text(
                    text = stringResource(R.string.remember_my_choise),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
            }

            //Список питомцев
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(vertical = 10.dp, horizontal = 25.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    PetItem( )
                    Spacer(modifier = Modifier.height(20.dp))
                    PetItem( )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            // кнопка добавления нового питомца в список
            Button(
                modifier = Modifier.padding(20.dp),
                onClick = { }
            ) {
                Text(text = stringResource(id = R.string.add_button_description))
            }
        }
    }
}


@Composable
fun PetItem() {
    Card (modifier = Modifier
        .clickable { }
        .fillMaxWidth()
    ){
        Row (modifier = Modifier
            .padding(20.dp)
        ){
            Image(painter = painterResource(id = R.drawable.pet_icon),
                contentDescription = "photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .height(80.dp)
                    .width(80.dp))
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Имя питомца",
                color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold
            )
        }
    }
}
