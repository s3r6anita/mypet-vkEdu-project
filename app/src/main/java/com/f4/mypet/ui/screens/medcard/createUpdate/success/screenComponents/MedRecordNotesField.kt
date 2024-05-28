package com.f4.mypet.ui.screens.medcard.createUpdate.success.screenComponents

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.f4.mypet.R

@Composable
fun MedRecordNotesField(
    onNotesChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    givenNotes: String
) {
    var notes by remember { mutableStateOf(givenNotes) }
    notes = givenNotes

    OutlinedTextField(
        value = notes,
        onValueChange = { onNotesChange(it) },
        label = { Text(stringResource(R.string.cu_therapy_tmp_notice)) },
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(12.dp)
    )
}
