package com.f4.mypet.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.f4.mypet.ui.screens.medcard.MedCardScreen
import com.f4.mypet.ui.screens.procedure.createUpdate.CreateUpdateProcedureScreen
import com.f4.mypet.ui.screens.procedure.list.ListProcedureScreen
import com.f4.mypet.ui.screens.procedure.show.ProcedureScreen
import com.f4.mypet.ui.screens.profile.CreateUpdateProfileScreen
import com.f4.mypet.ui.screens.profile.ListProfileScreen
import com.f4.mypet.ui.screens.profile.show.ProfileScreen
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

        /** список профилей */
        composable(route = Routes.ListProfile.route) {
            ListProfileScreen(navController, snackbarHostState, scope)
        }
        /** профиль */
        composable(
            route = Routes.BottomBarRoutes.Profile.route + "/{profileId}" + "/{canNavigateBack}",
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
                scope,
                backStackEntry.arguments?.getInt("profileId") ?: -1,
                backStackEntry.arguments?.getBoolean("canNavigateBack") ?: true
            )
        }

        /** создание профиля */
        composable(route = Routes.CreateProfile.route) {
            CreateUpdateProfileScreen(
                navController,
                true,
                snackbarHostState,
                scope
            )
        }

        /** обновление профиля */
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
                backStackEntry.arguments?.getInt("profileId") ?: -1
            )
        }


        /** список процедур */
        composable(
            route = Routes.BottomBarRoutes.ListProcedures.route + "/{profileId}" + "/{canNavigateBack}",
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
                profileId = backStackEntry.arguments?.getInt("profileId") ?: -1,
                canNavigateBack = backStackEntry.arguments?.getBoolean("canNavigateBack") ?: true
            )
        }
    }
    /** создание процедуры */
    composable(route = Routes.CreateProcedure.route + "/{profileId}",
        arguments = listOf(
            navArgument(name = "profileId") {
                type = NavType.IntType
            }
        )
    ) { backStackEntry ->
        CreateUpdateProcedureScreen(
            navController,
            backStackEntry.arguments?.getInt("profileId") ?: -1
        )
    }

    /** процедура */
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
            backStackEntry.arguments?.getInt("profileId") ?: -1,
            backStackEntry.arguments?.getInt("procedureId") ?: -1
        )
    }

    /** изменение процедуры */
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
        CreateUpdateProcedureScreen(
            navController,
            backStackEntry.arguments?.getInt("profileId") ?: -1,
            backStackEntry.arguments?.getInt("procedureId") ?: -1
        )
    }


    /** медкарта */
    composable(
        route = Routes.BottomBarRoutes.MedCard.route + "/{profileId}" + "/{canNavigateBack}",
        arguments = listOf(
            navArgument(name = "profileId") {
                type = NavType.IntType
            },
            navArgument(name = "canNavigateBack") {
                type = NavType.BoolType
            }
        )
    ) { backStackEntry ->
        MedCardScreen(
            navController = navController,
            profileId = backStackEntry.arguments?.getInt("profileId") ?: -1,
            canNavigateBack = backStackEntry.arguments?.getBoolean("canNavigateBack") ?: true
        )
    }
}
