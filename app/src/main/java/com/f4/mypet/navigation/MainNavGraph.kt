package com.f4.mypet.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.f4.mypet.ui.screens.medcard.list.ListMedRecords
import com.f4.mypet.ui.screens.medcard.show.MedRecordScreen
import com.f4.mypet.ui.screens.procedure.createUpdate.CreateUpdateProcedureScreen
import com.f4.mypet.ui.screens.procedure.list.ListProcedureScreen
import com.f4.mypet.ui.screens.procedure.show.ProcedureScreen
import com.f4.mypet.ui.screens.profile.createUpdate.CreateUpdateProfileScreen
import com.f4.mypet.ui.screens.profile.list.ListProfileScreen
import com.f4.mypet.ui.screens.profile.show.ProfileScreen
import kotlinx.coroutines.CoroutineScope

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    globalScope: CoroutineScope
) {
    navigation(
        route = START,
        startDestination = Routes.ListProfile.route
    ) {

        /** список профилей */
        composable(route = Routes.ListProfile.route) {
            ListProfileScreen(
                snackbarHostState = snackbarHostState,
                globalScope = globalScope,
                navController = navController
            )
        }
        /** профиль */
        composable(
            route = "${Routes.BottomBarRoutes.Profile.route}/{profileId}/{canNavigateBack}",
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
                snackbarHostState = snackbarHostState,
                profileId = backStackEntry.arguments?.getInt("profileId") ?: -1,
                canNavigateBack = backStackEntry.arguments?.getBoolean("canNavigateBack") ?: true,
                navController = navController
            )
        }
        /** создание профиля */
        composable(route = Routes.CreateProfile.route) {
            CreateUpdateProfileScreen(
                isCreateScreen = true,
                snackbarHostState = snackbarHostState,
                globalScope = globalScope,
                navController = navController
            )
        }
        /** обновление профиля */
        composable(
            route = "${Routes.UpdateProfile.route}/{profileId}",
            arguments = listOf(
                navArgument(name = "profileId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            CreateUpdateProfileScreen(
                isCreateScreen = false,
                snackbarHostState = snackbarHostState,
                globalScope = globalScope,
                profileId = backStackEntry.arguments?.getInt("profileId") ?: -1,
                navController = navController
            )
        }


        /** список процедур */
        composable(
            route = "${Routes.BottomBarRoutes.ListProcedures.route}/{profileId}/{canNavigateBack}",
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
                profileId = backStackEntry.arguments?.getInt("profileId") ?: -1,
                canNavigateBack = backStackEntry.arguments?.getBoolean("canNavigateBack") ?: true,
                navController = navController
            )
        }
        /** процедура */
        composable(
            route = "${Routes.Procedure.route}/{procedureId}",
            arguments = listOf(
                navArgument(name = "procedureId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            ProcedureScreen(
                procedureId = backStackEntry.arguments?.getInt("procedureId") ?: -1,
                navController = navController
            )
        }
        /** создание процедуры */
        composable(
            route = "${Routes.CreateProcedure.route}/{profileId}",
            arguments = listOf(
                navArgument(name = "profileId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            CreateUpdateProcedureScreen(
                navController = navController,
                isCreateScreen = true,
                procedureId = backStackEntry.arguments?.getInt("profileId") ?: -1,
            )
        }
        /** изменение процедуры */
        composable(
            route = "${Routes.UpdateProcedure.route}/{procedureId}",
            arguments = listOf(
                navArgument(name = "procedureId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            CreateUpdateProcedureScreen(
                navController = navController,
                isCreateScreen = false,
                profileId = backStackEntry.arguments?.getInt("profileId") ?: -1,
                procedureId = backStackEntry.arguments?.getInt("procedureId") ?: -1,
            )
        }


        /** список медицинких записей (медкарта) */
        composable(
            route = "${Routes.BottomBarRoutes.ListMedRecords.route}/{profileId}/{canNavigateBack}",
            arguments = listOf(
                navArgument(name = "profileId") {
                    type = NavType.IntType
                },
                navArgument(name = "canNavigateBack") {
                    type = NavType.BoolType
                }
            )
        ) { backStackEntry ->
            ListMedRecords(
                profileId = backStackEntry.arguments?.getInt("profileId") ?: -1,
                canNavigateBack = backStackEntry.arguments?.getBoolean("canNavigateBack") ?: true,
                navController = navController
            )
        }
        /** медицинская запись */
        composable(
            route = "${Routes.MedRecord.route}/{medRecordId}",
            arguments = listOf(
                navArgument(name = "medRecordId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            MedRecordScreen(
                medRecordId = backStackEntry.arguments?.getInt("medRecordId") ?: -1,
                navController = navController
            )
        }
        /** создание медицинской записи */
        composable(
            route = "${Routes.CreateMedRecord.route}/{profileId}",
            arguments = listOf(
                navArgument(name = "profileId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
//            CreateUpdateMedRecordScreen(
//                isCreateScreen = true,
//                backStackEntry.arguments?.getInt("profileId") ?: -1,
//                navigateUp = { navController.navigateUp() },
//                navigate = { route, builderOptions ->
//                    navController.navigate(route, builderOptions)
//                }
//            )
        }
        /** изменение медицинской записи */
        composable(
            route = "${Routes.UpdateMedRecord.route}/{medRecordId}",
            arguments = listOf(
                navArgument(name = "medRecordId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
//            CreateUpdateMedRecordScreen(
//                isCreateScreen = false,
//                procedureId = backStackEntry.arguments?.getInt("procedureId") ?: -1,
//                navigateUp = { navController.navigateUp() },
//                navigate = { route, builderOptions ->
//                    navController.navigate(route, builderOptions)
//                }
//            )
        }
    }
}
