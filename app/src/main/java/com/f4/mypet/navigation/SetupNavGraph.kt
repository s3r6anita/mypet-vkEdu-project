package com.f4.mypet.navigation

import androidx.compose.animation.EnterTransition
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

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = START,
        enterTransition = { EnterTransition.None },
/**
    Отключение анимаций перехода между экранами
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
 */
    ) {
        NavGraph(navController)
    }
}

fun NavGraphBuilder.NavGraph(
    navController: NavHostController
) {
    navigation(
        route = START,
        startDestination = Routes.ListProfile.route
    ) {
//        список профилей
        composable(route = Routes.ListProfile.route) {
            ListProfileScreen(navController)
        }

//        создание профиля
        composable(route = Routes.CreateProfile.route) {
            CreateUpdateProfileScreen(navController,true)
        }

//        профиль
        composable(
            route = Routes.Profile.route + "/{id}",
            arguments = listOf(navArgument(name = "id") {
                type = NavType.IntType
                defaultValue = 10
            })
        ) { backStackEntry ->
            ProfileScreen(
                navController,
                backStackEntry.arguments?.getInt("id")
            )
        }

//        обновление профиля
        composable(
            route = Routes.UpdateProfile.route + "/{id}",
            arguments = listOf(navArgument(name = "id") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            CreateUpdateProfileScreen(
                navController,
                false,
                backStackEntry.arguments?.getInt("id")
            )
        }
    }
}