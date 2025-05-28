package com.marky.strivefit.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun StriveFitApp() {
        val navController = rememberNavController()

        val isUserLoggedIn = false // placeholder
        val isUserSetupComplete = false // placeholder

        val startDestination = when {
            !isUserLoggedIn -> "welcome"
            !isUserSetupComplete -> "userSetupHome"
            else -> "mainApp"
        }

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
                    startDestination = startDestination
                ) {
                    onboardingNavGraph(navController)
                    userSetupNavGraph(navController)
                    mainAppNavGraph(navController)
                }
            }
    }
}
