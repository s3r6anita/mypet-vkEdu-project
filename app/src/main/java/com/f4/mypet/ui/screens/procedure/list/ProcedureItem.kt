package com.f4.mypet.ui.screens.procedure.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.navigation.NavOptionsBuilder
import com.f4.mypet.PetDateTimeFormatter
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.navigation.Routes
import com.f4.mypet.ui.theme.LightBlueBackground


@Composable
fun ProcedureItem(
    procedure: Procedure,
    title: String,
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
            .padding(bottom = 15.dp)
            .clickable {
                navigate(Routes.Procedure.route + "/" + procedure.id) {
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
            ) {
                Image(
                    painter = painterResource(id = R.drawable.procedures_icon),
                    contentDescription = stringResource(id = R.string.list_procedure_screen_title),
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(LightBlueBackground)
                        .size(50.dp),
                )
                Column(
                    modifier = Modifier
                        .padding(start = 20.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = title,
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
                        text = procedure.dateDone.format(PetDateTimeFormatter.dateTime),
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
