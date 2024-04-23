package com.f4.mypet.ui.screens.profile.show.screencomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.f4.mypet.PetDateTimeFormatter
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.ui.components.TextComponent
import com.f4.mypet.ui.theme.LightBlueBackground

@Composable
fun ProfileItem(
    pet: Pet
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
        ),
        modifier = Modifier
            .padding(top = 50.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.information_about_pet),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 20.dp),
            )
            TextComponent(
                header = stringResource(R.string.pet_name), value = pet.name
            )
            TextComponent(
                header = stringResource(R.string.pet_view), value = pet.kind
            )
            TextComponent(
                header = stringResource(R.string.pet_breed), value = pet.breed
            )
            TextComponent(
                header = stringResource(R.string.pet_sex), value = pet.sex
            )
            TextComponent(
                header = stringResource(R.string.pet_birthday),
                value = pet.birthday.format(PetDateTimeFormatter.date)
            )
            TextComponent(
                header = stringResource(R.string.pet_coat), value = pet.coat
            )
            TextComponent(
                header = stringResource(R.string.pet_color), value = pet.color
            )
            TextComponent(
                header = stringResource(R.string.pet_microchip),
                value = pet.microchipNumber
            )
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.pet_icon),
            contentDescription = stringResource(id = R.string.pet_photo_description),
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(LightBlueBackground)
        )
    }
}
