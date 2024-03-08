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
//        Отключение анимаций перехода между экранами
        enterTransition = { EnterTransition.None },
       // exitTransition = { ExitTransition.None },
       // popEnterTransition = { EnterTransition.None },
       // popExitTransition = { ExitTransition.None },
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
            route = Routes.Profile.route + "/{profileId}",
            arguments = listOf(navArgument(name = "profileId") {
                type = NavType.StringType
                defaultValue = "0"
                nullable = true
            })
        ) { backStackEntry ->
            ProfileScreen(
                navController,
                backStackEntry.arguments?.getString("profileId")
            )
        }

//        обновление профиля
        composable(
            route = Routes.UpdateProfile.route + "/{profileId}",
            arguments = listOf(navArgument(name = "profileId") {
                type = NavType.StringType
                defaultValue = "0"
                nullable = true
            })
        ) { backStackEntry ->
            CreateUpdateProfileScreen(
                navController,
                false,
                backStackEntry.arguments?.getString("profileId")
            )
        }
    }
}