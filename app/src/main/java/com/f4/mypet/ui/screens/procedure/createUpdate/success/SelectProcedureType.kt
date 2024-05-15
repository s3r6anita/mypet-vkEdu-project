package com.f4.mypet.ui.screens.procedure.createUpdate.success

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.f4.mypet.R
import com.f4.mypet.data.db.entities.ProcedureType
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectProcedureType(
    types: ImmutableList<ProcedureType>,
    selectedType: ProcedureType,
    dropdownMenuColors: TextFieldColors,
    changeSelectedType: (ProcedureType) -> Unit
) {
    // Тип процедуры - выпадающее меню с выбором

    var typeExpanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = typeExpanded,
        onExpandedChange = {
            typeExpanded = it
        }
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            readOnly = true,
            value = selectedType.name,
            onValueChange = { },
            label = { Text(stringResource(R.string.creation_procedure_screen_procedure_type)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = typeExpanded
                )
            },
            colors = dropdownMenuColors
        )
        ExposedDropdownMenu(
            expanded = typeExpanded,
            onDismissRequest = {
                typeExpanded = false
            }
        ) {
            types.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption.name) },
                    onClick = {
                        changeSelectedType(selectionOption)
                        typeExpanded = false
                    }
                )
            }
        }
    }
}
