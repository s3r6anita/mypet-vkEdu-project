package com.f4.mypet.navigation

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.f4.mypet.ui.screens.auth.login.LoginScreen
import com.f4.mypet.ui.screens.auth.registration.RegistrationScreen
import com.f4.mypet.ui.screens.medcard.createUpdate.CreateUpdateMedRecordScreen
import com.f4.mypet.ui.screens.medcard.list.ListMedRecords
import com.f4.mypet.ui.screens.medcard.show.MedRecordScreen
import com.f4.mypet.ui.screens.procedure.createUpdate.CreateUpdateProcedureScreen
import com.f4.mypet.ui.screens.procedure.list.ListProcedureScreen
import com.f4.mypet.ui.screens.procedure.show.ProcedureScreen
import com.f4.mypet.ui.screens.profile.createUpdate.CreateUpdateProfileScreen
import com.f4.mypet.ui.screens.profile.list.ListProfileScreen
import com.f4.mypet.ui.screens.profile.show.ProfileScreen
import com.f4.mypet.ui.screens.wall.PetsWallScreen
import kotlinx.coroutines.CoroutineScope

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    globalScope: () -> CoroutineScope,
    context: Context
) {
    navigation(
        route = START,
        startDestination = Routes.Login.route

    ) {

        /** вход в аккаунт */
        composable(route = Routes.Login.route) {
            LoginScreen(
                navController = navController,
                context = context
            )
        }

        /** создание аккаунта */
        composable(route = Routes.Register.route) {
            RegistrationScreen(
                navController = navController,
            )
        }

        /** список профилей */
        composable(route = Routes.ListProfile.route) {
            ListProfileScreen(
                navController = navController,
                snackbarHostState = snackbarHostState,
                globalScope = globalScope
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
                navController = navController,
                snackbarHostState = snackbarHostState,
                profileId = backStackEntry.arguments?.getInt("profileId") ?: -1,
                canNavigateBack = backStackEntry.arguments?.getBoolean("canNavigateBack") ?: true
            )
        }
        /** создание профиля */
        composable(route = Routes.CreateProfile.route) {
            CreateUpdateProfileScreen(
                navController = navController,
                snackbarHostState = snackbarHostState,
                isCreateScreen = true,
                globalScope = globalScope
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
                navController = navController,
                snackbarHostState = snackbarHostState,
                isCreateScreen = false,
                globalScope = globalScope,
                profileId = backStackEntry.arguments?.getInt("profileId") ?: -1
            )
        }


        /** стена */
        composable(
            route = "${Routes.BottomBarRoutes.PetsWall.route}/{profileId}/{canNavigateBack}",
            arguments = listOf(
                navArgument(name = "profileId") {
                    type = NavType.IntType
                },
                navArgument(name = "canNavigateBack") {
                    type = NavType.BoolType
                }
            )
        ) { backStackEntry ->
            PetsWallScreen(
                navController = navController,
                canNavigateBack = backStackEntry.arguments?.getBoolean("canNavigateBack") ?: true
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
                navController = navController,
                profileId = backStackEntry.arguments?.getInt("profileId") ?: -1,
                canNavigateBack = backStackEntry.arguments?.getBoolean("canNavigateBack") ?: true
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
                navController = navController,
                procedureId = backStackEntry.arguments?.getInt("procedureId") ?: -1
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
                procedureId = backStackEntry.arguments?.getInt("profileId") ?: -1
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
                procedureId = backStackEntry.arguments?.getInt("procedureId") ?: -1
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
                navController = navController,
                profileId = backStackEntry.arguments?.getInt("profileId") ?: -1,
                canNavigateBack = backStackEntry.arguments?.getBoolean("canNavigateBack") ?: true
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
                navController = navController,
                medRecordId = backStackEntry.arguments?.getInt("medRecordId") ?: -1
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
            CreateUpdateMedRecordScreen(
                navController = navController,
                isCreateScreen = true,
                profileId = backStackEntry.arguments?.getInt("profileId") ?: -1,
            )
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
            CreateUpdateMedRecordScreen(
                navController = navController,
                isCreateScreen = false,
                profileId = backStackEntry.arguments?.getInt("profileId") ?: -1,
            )
        }
    }
}
