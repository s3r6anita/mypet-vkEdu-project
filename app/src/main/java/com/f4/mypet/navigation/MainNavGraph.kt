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
                navigate = { route, builderOptions ->
                    navController.navigate(route, builderOptions)
                }
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
                navigateUp = { navController.navigateUp() },
                navigate = { route, builderOptions ->
                    navController.navigate(route, builderOptions)
                }
            )
        }
        /** создание профиля */
        composable(route = Routes.CreateProfile.route) {
            CreateUpdateProfileScreen(
                isCreateScreen = true,
                snackbarHostState = snackbarHostState,
                globalScope = globalScope,
                navigateUp = { navController.navigateUp() },
                navigateListProfile = {
                    navController.navigate(Routes.ListProfile.route) {
                        popUpTo(Routes.ListProfile.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
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
                navigateUp = { navController.navigateUp() }
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
                navigateUp = { navController.navigateUp() },
                navigate = { route, builderOptions ->
                    navController.navigate(route, builderOptions)
                }
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
                navigateUp = { navController.navigateUp() },
                navigateUpdateProcedure = { procedureId, _ ->
                    navController.navigate("${Routes.UpdateProcedure.route}/$procedureId") {
                        launchSingleTop = true
                    }
                }
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
                isCreateScreen = true,
                profileId = backStackEntry.arguments?.getInt("profileId") ?: -1,
                navigateUp = { navController.navigateUp() },
//                navigateListProcedures = {
//                    navController.navigate("${Routes.BottomBarRoutes.ListProcedures.route}/$profile") {
//                        popUpTo(Routes.BottomBarRoutes.ListProcedures.route) {
//                            inclusive = true
//                        }
//                        launchSingleTop = true
//                    }
//                }
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
                isCreateScreen = false,
                profileId = backStackEntry.arguments?.getInt("profileId") ?: -1,
                procedureId = backStackEntry.arguments?.getInt("procedureId") ?: -1,
                navigateUp = { navController.navigateUp() },
//                navigateListProcedures = {}
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
                navigateUp = { navController.navigateUp() },
                navigate = { route, builderOptions ->
                    navController.navigate(route, builderOptions)
                }
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
                navigateUp = { navController.navigateUp() },
                navigateUpdateMedRecord = { medRecordId ->
                    navController.navigate("${Routes.UpdateMedRecord.route}/$medRecordId") {
                        launchSingleTop = true
                    }
                }
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
