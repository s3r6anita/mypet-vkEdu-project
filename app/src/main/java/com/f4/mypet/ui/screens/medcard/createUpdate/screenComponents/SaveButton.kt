package com.f4.mypet.ui.screens.medcard.createUpdate.screenComponents
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.f4.mypet.PastOrPresentSelectableDates
import com.f4.mypet.PetDateTimeFormatter
import com.f4.mypet.R
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.OutlinedTextFieldColor
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun SaveButton() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.BottomCenter),
            onClick = {
                // TODO: переход
            },
            colors = ButtonDefaults.buttonColors(containerColor = GreenButton)
        ) {
            Text(
                text = stringResource(R.string.save_button_description),
                modifier = Modifier.padding(start = 10.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
