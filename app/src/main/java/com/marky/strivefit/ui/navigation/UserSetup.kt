package com.marky.strivefit.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.marky.strivefit.ui.screens.userSetup.LoadingScreen
import com.marky.strivefit.ui.screens.userSetup.AgeInputScreen
import com.marky.strivefit.ui.screens.userSetup.BodyFocusAreasScreen
import com.marky.strivefit.ui.screens.userSetup.Entry
import com.marky.strivefit.ui.screens.userSetup.EquipmentSelectionScreen
import com.marky.strivefit.ui.screens.userSetup.ExperienceLevelScreen
import com.marky.strivefit.ui.screens.userSetup.GoalsInputScreen
import com.marky.strivefit.ui.screens.userSetup.HeightInputScreen
import com.marky.strivefit.ui.screens.userSetup.WeightInputScreen
import com.marky.strivefit.ui.screens.userSetup.WorkoutPreferencesScreen
import com.marky.strivefit.ui.viewModel.NavOrigin
import popTransitionAnimation
import transitionAnimation

fun NavGraphBuilder.userSetupNavGraph(navController: NavHostController) {

    val slideInLeft = transitionAnimation(AnimatedContentTransitionScope.SlideDirection.Left, 300)
    val slideOutLeft = popTransitionAnimation(AnimatedContentTransitionScope.SlideDirection.Left, 300)
    val slideInRight = transitionAnimation(AnimatedContentTransitionScope.SlideDirection.Right, 300)
    val slideOutRight = popTransitionAnimation(AnimatedContentTransitionScope.SlideDirection.Right, 300)
    composable(
        route = "SetupEntry?origin={origin}",
        arguments = listOf(
            navArgument("origin") {
                defaultValue = "user"
            }
        ),
        enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 300))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 300))
        }
    ) { backStackEntry ->
        SetupEntryScreen(
            backStackEntry,
            navController
        )
    }

    composable(
        route = "age_input",
        enterTransition = { slideInLeft },
        exitTransition = { slideOutLeft },
        popEnterTransition = { slideInRight },
        popExitTransition = { slideOutRight }
    ) {
        AgeInputScreen(
            onBackClick = { navController.popBackStack() },
            onContinueClick = { navController.navigate("height_input") }
        )
    }

    composable(
        route = "height_input",
        enterTransition = { slideInLeft },
        exitTransition = { slideOutLeft },
        popEnterTransition = { slideInRight },
        popExitTransition = { slideOutRight }
    ) {
        HeightInputScreen(
            onBackClick = { navController.popBackStack() },
            onContinueClick = { navController.navigate("weight_input") }
        )
    }

    composable(
        route = "weight_input",
        enterTransition = { slideInLeft },
        exitTransition = { slideOutLeft },
        popEnterTransition = { slideInRight },
        popExitTransition = { slideOutRight }
    ) {
        WeightInputScreen(
            onBackClick = { navController.popBackStack() },
            onContinueClick = { navController.navigate("goal_selection") }
        )
    }

    composable(
        route = "goal_selection",
        enterTransition = { slideInLeft },
        exitTransition = { slideOutLeft },
        popEnterTransition = { slideInRight },
        popExitTransition = { slideOutRight }
    ) {
        GoalsInputScreen(
            onBackClick = { navController.popBackStack() },
            onContinueClick = { navController.navigate("experience_selection") }
        )
    }

    composable(
        route = "experience_selection",
        enterTransition = { slideInLeft },
        exitTransition = { slideOutLeft },
        popEnterTransition = { slideInRight },
        popExitTransition = { slideOutRight }
    ) {
        ExperienceLevelScreen(
            onBackClick = { navController.popBackStack() },
            onContinueClick = { navController.navigate("body_selection") }
        )
    }

    composable(
        route = "body_selection",
        enterTransition = { slideInLeft },
        exitTransition = { slideOutLeft },
        popEnterTransition = { slideInRight },
        popExitTransition = { slideOutRight }
    ) {
        BodyFocusAreasScreen(
            onBackClick = { navController.popBackStack() },
            onContinueClick = { navController.navigate("equipment_selection") }
        )
    }


    composable(
        route = "equipment_selection",
        enterTransition = { slideInLeft },
        exitTransition = { slideOutLeft },
        popEnterTransition = { slideInRight },
        popExitTransition = { slideOutRight }
    ) {
        EquipmentSelectionScreen(
            onBackClick = { navController.popBackStack() },
            onContinueClick = { navController.navigate("workoutPreferences_selection") }
        )
    }

    composable(
        route = "workoutPreferences_selection",
        enterTransition = { slideInLeft },
        exitTransition = { slideOutLeft },
        popEnterTransition = { slideInRight },
        popExitTransition = { slideOutRight }
    ) {
        WorkoutPreferencesScreen(
            onBackClick = { navController.popBackStack() },
            onCreatePlanClick = { navController.navigate("gettingReady") }
        )
    }

    composable(
        route = "gettingReady",
        enterTransition = { slideInLeft },
        exitTransition = { slideOutLeft },
        popEnterTransition = { slideInRight },
        popExitTransition = { slideOutRight }
    ) {
        LoadingScreen(onComplete = {navController.navigate("mainApp"){
            popUpTo(0) {inclusive = true}
                }
            }
        )
    }
}

@Composable
fun SetupEntryScreen(
    backStackEntry: NavBackStackEntry,
    navController: NavHostController
) {
    val origin = backStackEntry.arguments?.getString("origin") ?: "guest"
    val navOrigin: NavOrigin = viewModel()
    navOrigin.setOrigin(origin)

    Entry(
        isSignedIn = origin == "guest",
        onContinueClick = { nickname ->
            navController.navigate("age_input")
        }
    )
}