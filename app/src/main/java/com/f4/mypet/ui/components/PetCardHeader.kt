package com.f4.mypet.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.f4.mypet.R

@Composable
fun PetCardHeader(
    petName: String,
    backgroundColor: Color
){
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
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
                text = petName,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
    }
}
