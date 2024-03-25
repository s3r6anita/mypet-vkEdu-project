package com.f4.mypet.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.f4.mypet.ui.screens.procedure.ListProcedureScreen
import com.f4.mypet.ui.screens.procedure.ProcedureScreen
import com.f4.mypet.ui.screens.profile.CreateUpdateProfileScreen
import com.f4.mypet.ui.screens.profile.ListProfileScreen
import com.f4.mypet.ui.screens.profile.ProfileScreen
import kotlinx.coroutines.CoroutineScope


fun NavGraphBuilder.mainNavGraph(
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
//        профиль
        composable(
            route = BottomBarRoutes.Profile.route + "/{profileId}" + "/{canNavigateBack}",
            arguments = listOf(
                navArgument(name = "profileId") {
                    type = NavType.IntType
                },
                navArgument(name = "canNavigateBack") {
                    type = NavType.BoolType
                }
            )
        ) { backStackEntry ->
            ProfileScreen(
                navController,
                snackbarHostState,
                backStackEntry.arguments?.getInt("profileId"),
                backStackEntry.arguments?.getBoolean("canNavigateBack")
            )
        }

//        создание профиля
        composable(route = Routes.CreateProfile.route) {
            CreateUpdateProfileScreen(navController, true, snackbarHostState, scope)
        }

//        обновление профиля
        composable(
            route = Routes.UpdateProfile.route + "/{profileId}",
            arguments = listOf(
                navArgument(name = "profileId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            CreateUpdateProfileScreen(
                navController,
                false,
                snackbarHostState,
                scope,
                backStackEntry.arguments?.getInt("profileId")
            )
        }



//        список процедур
        composable(
            route = BottomBarRoutes.ListProcedures.route + "/{profileId}" + "/{canNavigateBack}",
            arguments = listOf(
                navArgument(name = "profileId") {
                    type = NavType.IntType
                },
                navArgument(name = "canNavigateBack") {
                    type = NavType.BoolType
                }
            )
        ) { backStackEntry ->
            ListProcedureScreen(
                navController = navController,
                profileId = backStackEntry.arguments?.getInt("profileId"),
                canNavigateBack = backStackEntry.arguments?.getBoolean("canNavigateBack")
            )
        }
    }
//        создание процедуры
        composable(route = Routes.CreateProcedure.route + "/{profileId}",
            arguments = listOf(
                navArgument(name = "profileId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            //CreateProcedureScreen(navController, context, backStackEntry.arguments?.getString("profileId"))
        }

//        процедура
        composable(route = Routes.Procedure.route + "/{profileId}" + "/{procedureId}",
            arguments = listOf(
                navArgument(name = "profileId") {
                    type = NavType.IntType
                },
                navArgument(name = "procedureId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            ProcedureScreen(
                navController,
                backStackEntry.arguments?.getInt("profileId"),
                backStackEntry.arguments?.getInt("procedureId")
            )
        }

//        изменение процедуры
        composable(route = Routes.UpdateProcedure.route + "/{profileId}" + "/{procedureId}",
            arguments = listOf(
                navArgument(name = "profileId") {
                    type = NavType.IntType
                },
                navArgument(name = "procedureId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
//            UpdateProcedureScreen(
//                navController,
//                context,
//                backStackEntry.arguments?.getString("profileId"),
//                backStackEntry.arguments?.getString("procedureId")
//            )
        }



//        медкарта
    composable(
        route = BottomBarRoutes.MedCard.route + "/{profileId}" + "/{canNavigateBack}",
        arguments = listOf(
            navArgument(name = "profileId") {
                type = NavType.IntType
            },
            navArgument(name = "canNavigateBack") {
                type = NavType.BoolType
            }
        )
    ) { // backStackEntry ->
//        MedCardScreen(
//            navController = navController,
//            profileId = backStackEntry.arguments?.getInt("profileId"),
//            canNavigateBack = backStackEntry.arguments?.getBoolean("canNavigateBack")
//        )
    }
}
