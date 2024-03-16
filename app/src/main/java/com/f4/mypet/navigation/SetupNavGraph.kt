package com.f4.mypet.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.f4.mypet.ui.screens.profile.CreateUpdateProfileScreen
import com.f4.mypet.ui.screens.profile.ListProfileScreen
import com.f4.mypet.ui.screens.profile.ProfileScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
) {
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
        NavGraph(navController, snackbarHostState, scope)
    }
}

fun NavGraphBuilder.NavGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {
    navigation(
        route = START,
        startDestination = Routes.ListProfile.route
    ) {
//        список профилей
        composable(route = Routes.ListProfile.route) {
            ListProfileScreen(navController, snackbarHostState, scope)
        }

//        создание профиля
        composable(route = Routes.CreateProfile.route) {
            CreateUpdateProfileScreen(navController, true, snackbarHostState, scope)
        }

//        профиль
        composable(
            route = Routes.Profile.route + "/{id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            ProfileScreen(
                navController,
                snackbarHostState,
                backStackEntry.arguments?.getInt("id")
            )
        }

//        обновление профиля
        composable(
            route = Routes.UpdateProfile.route + "/{id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            CreateUpdateProfileScreen(
                navController,
                false,
                snackbarHostState,
                scope,
                backStackEntry.arguments?.getInt("id")
            )
        }
    }
}
