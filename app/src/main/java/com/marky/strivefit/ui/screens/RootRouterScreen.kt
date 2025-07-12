package com.marky.strivefit.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.marky.strivefit.ui.viewModel.AuthViewModel
import com.marky.strivefit.ui.viewModel.MainViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.marky.strivefit.ui.navigation.MainAppGraph
import com.marky.strivefit.ui.navigation.UserSetup
import com.marky.strivefit.ui.navigation.Welcome


@Composable
fun RootRouterScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val isSignedIn by authViewModel.isSignedIn.collectAsState()
    val isUserSetupComplete by mainViewModel.isUserSetupComplete.collectAsState()

    LaunchedEffect(isSignedIn, isUserSetupComplete) {
        if (isSignedIn == null || isUserSetupComplete == null) {
            return@LaunchedEffect
        }

        val destination = when {
            !isSignedIn -> Welcome
            !isUserSetupComplete -> UserSetup
            else -> MainAppGraph
        }

        navController.navigate(destination) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}