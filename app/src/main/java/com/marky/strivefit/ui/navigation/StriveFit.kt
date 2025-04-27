package com.marky.strivefit.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.marky.strivefit.ui.theme.DefaultDark

@Composable
fun StriveFitApp(isDarkTheme: Boolean) {
    val navController = rememberNavController()
    val backgroundColor = if (isDarkTheme) DefaultDark.background else MaterialTheme.colorScheme.background

    val isUserLoggedIn = false// placeholder
    val isUserSetupComplete = false// placeholder

    val startDestination = when {
        !isUserLoggedIn -> "welcome"
        !isUserSetupComplete -> "userSetupHome"
        else -> "mainApp"
    }

    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
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
