package com.f4.mypet.ui.screens.bugReport.screenComponents

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.f4.mypet.R
@Composable
fun BugReportDescriptionField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.bug_report_description)) },
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
            .height(120.dp),
        shape = RoundedCornerShape(12.dp),
    )
}
