package com.f4.mypet.ui.screens.medcard.show.screenComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.MedRecord
import com.f4.mypet.ui.components.TextComponent
import com.f4.mypet.util.PetDateTimeFormatter

@Composable
fun ShowMedRecordData(medRecord: MedRecord) {
    Card(
        modifier = Modifier
            .padding(top = 50.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
        )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = medRecord.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 20.dp),
            )
            TextComponent(
                header = stringResource(R.string.therapy_date),
                value = medRecord.date.format(PetDateTimeFormatter.date)
            )
            TextComponent(
                header = stringResource(R.string.procedure_screen_time_of_event),
                value = medRecord.date.format(PetDateTimeFormatter.time)
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    readOnly = true,
                    value = medRecord.notes,
                    shape = RoundedCornerShape(12.dp),
                    placeholder = {},
                    onValueChange = { },
                    label = { Text(stringResource(id = R.string.medrecord_screen_notes)) },
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                        .padding(bottom = 15.dp)
                )
            }
        }
    }
}
