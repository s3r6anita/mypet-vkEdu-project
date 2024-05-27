package com.f4.mypet.ui.screens.procedure.createUpdate.success.screenComponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.ui.screens.procedure.createUpdate.CreateUpdateProcedureViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrequencySelector(
    procedure: Procedure,
    isCreateScreen: Boolean,
    onProcedureChange: (Procedure) -> Unit,
    viewModel: CreateUpdateProcedureViewModel = hiltViewModel()
) {

    val frequencyOptions = viewModel.frequencyOptions

    var frequencyExpanded by remember { mutableStateOf(false) }
    var selectedFrequency by remember {
        mutableStateOf(
            if (isCreateScreen)
                frequencyOptions[0]
            else
                viewModel.frequency
        )
    }
    var frequencyInProcedure by remember { mutableStateOf(procedure.frequency) }

    ExposedDropdownMenuBox(
        expanded = frequencyExpanded,
        onExpandedChange = {
            frequencyExpanded = it
        },
        modifier = Modifier.padding(bottom = 5.dp)
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            value = selectedFrequency.option,
            readOnly = true,
            label = { Text(stringResource(R.string.creation_procedure_screen_frequence)) },
            onValueChange = { },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = frequencyExpanded)
            },
            colors = getDropdownMenuColors(),
        )
        ExposedDropdownMenu(
            expanded = frequencyExpanded,
            onDismissRequest = {
                frequencyExpanded = false
            }
        ) {
            frequencyOptions.forEach() { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption.option) },
                    onClick = {
                        selectedFrequency = selectionOption
                        onProcedureChange(procedure.copy(frequencyOption = selectionOption.id))
                        frequencyExpanded = false
                    }
                )
            }
        }
    }
    if (selectedFrequency != frequencyOptions.first()) {
        OutlinedTextField(
            value = frequencyInProcedure.toString(),
            onValueChange = {
                frequencyInProcedure = it
                onProcedureChange(procedure.copy(frequency = frequencyInProcedure))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            label = { Text(text = stringResource(R.string.creation_procedure_screen_period)) },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    frequencyInProcedure = ""
                }) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = stringResource(id = R.string.clear)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            shape = RoundedCornerShape(12.dp),
            colors = getOutLinedTextFieldColors()
        )
    }
}