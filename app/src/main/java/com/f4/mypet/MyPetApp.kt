package com.f4.mypet

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
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
            navController = rememberNavController()
        )
    }
}