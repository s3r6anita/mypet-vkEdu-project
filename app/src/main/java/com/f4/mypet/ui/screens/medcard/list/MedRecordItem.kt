package com.f4.mypet.ui.screens.medcard.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptionsBuilder
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.MedRecord
import com.f4.mypet.navigation.Routes
import com.f4.mypet.ui.theme.LightBlueBackground
import com.f4.mypet.ui.theme.LightGrayTint
import com.f4.mypet.util.PetDateTimeFormatter

@Composable
fun MedRecordItem(
    medRecord: MedRecord,
    navigate: (String, NavOptionsBuilder.() -> Unit) -> Unit
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
            .padding(bottom = 15.dp)
            .clickable {
                navigate("${Routes.MedRecord.route}/${medRecord.id}") {
                    launchSingleTop = true
                }
            }
    ) {
        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.therapy_icon),
                    contentDescription = stringResource(id = R.string.pet_photo_description),
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(LightBlueBackground)
                        .size(50.dp),
                )
                Column(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .height(50.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = medRecord.title.toString(), // TODO: убрать toString() после того, как изменим Entity
                        color = Color.Black,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = medRecord.date.format(PetDateTimeFormatter.dateTime),
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
