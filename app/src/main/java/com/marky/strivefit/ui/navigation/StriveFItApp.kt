package com.marky.strivefit.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.marky.strivefit.ui.viewModel.AuthViewModel
import androidx.compose.runtime.getValue

@Composable
fun StriveFitApp() {
        val auth: AuthViewModel = hiltViewModel()
        val isSignedIn by auth.isSignedIn.collectAsState()
        val navController = rememberNavController()
        val isUserSetupComplete = false // placeholder

        val startDestination = when {
            !isSignedIn -> AppState.WELCOME
            !isUserSetupComplete -> AppState.USER_SETUP
            else -> AppState.MAIN_APP
        }

        val route = startDestination.route()

        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = route
                ) {
                    onboardingNavGraph(navController)
                    userSetupNavGraph(navController)
                    mainAppNavGraph(navController)
                }
            }
    }
}

enum class AppState {
    WELCOME,
    USER_SETUP,
    MAIN_APP
}

fun AppState.route(): String = when(this) {
    AppState.WELCOME -> "welcome"
    AppState.USER_SETUP -> "userSetupHome"
    AppState.MAIN_APP -> "mainApp"
}
