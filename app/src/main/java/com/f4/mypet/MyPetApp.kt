package com.f4.mypet

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.f4.mypet.navigation.START
import com.f4.mypet.navigation.SetupNavGraph
import com.f4.mypet.ui.theme.MyPetTheme


@Composable
fun MyPetApp(notificationRoute: String?) {
    MyPetTheme {
        SetupNavGraph(
            navController = rememberNavController(),
            snackbarHostState = remember { SnackbarHostState() },
            notificationRoute = notificationRoute
        )
    }
}
