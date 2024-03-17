package com.f4.mypet

import android.annotation.SuppressLint
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.f4.mypet.navigation.SetupNavGraph
import com.f4.mypet.ui.theme.MyPetTheme
import java.text.SimpleDateFormat

// Формат даты и времени
@SuppressLint("SimpleDateFormat")
val dateFormat = SimpleDateFormat("dd.MM.yyyy")
@SuppressLint("SimpleDateFormat")
val timeFormat = SimpleDateFormat("HH:mm")

@Composable
fun MyPetApp() {
    MyPetTheme {
        SetupNavGraph(
            navController = rememberNavController(),
            snackbarHostState = remember { SnackbarHostState() },
            scope = rememberCoroutineScope()
        )
    }
}
