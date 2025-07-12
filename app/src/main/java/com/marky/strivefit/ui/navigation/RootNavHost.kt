package com.marky.strivefit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marky.strivefit.data.values.AppState
import com.marky.strivefit.ui.screens.RootRouterScreen

@Composable
fun RootNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = root_router
    ) {
        composable<root_router>() {
            RootRouterScreen(navController = navController)
        }

            OnboardingNavGraph(navController)
            MainAppNavGraph(navController)
            UserSetupNavGraph(navController)
    }
}

fun AppState.route(): Any = when(this) {
    AppState.WELCOME -> Welcome
    AppState.USER_SETUP -> UserSetup
    AppState.MAIN_APP -> MainAppGraph
}