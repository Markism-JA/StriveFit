package com.marky.strivefit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marky.strivefit.data.values.AppState
import com.marky.strivefit.ui.screens.RootRouterScreen
import kotlinx.serialization.Serializable

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

@Serializable
object PostAuthWelcome

@Serializable
object MainApp

@Serializable
object UserSetup


fun AppState.route(): Any = when(this) {
    AppState.WELCOME -> Welcome
    AppState.USER_SETUP -> MainApp
    AppState.MAIN_APP -> UserSetup
}