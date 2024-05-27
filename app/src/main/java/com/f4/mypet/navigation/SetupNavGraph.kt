package com.f4.mypet.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    val getGlobalScope = { scope }

    NavHost(
        navController = navController,
        startDestination = START,
        enterTransition = { EnterTransition.None },
/**
*    Отключение анимаций перехода между экранами
*       exitTransition = { ExitTransition.None },
*       popEnterTransition = { EnterTransition.None },
*       popExitTransition = { ExitTransition.None },
*/
    ) {
        mainNavGraph(navController, snackbarHostState, getGlobalScope)
    }
}
