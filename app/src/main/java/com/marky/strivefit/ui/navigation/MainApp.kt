package com.marky.strivefit.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.marky.strivefit.ui.screens.mainApp.MainAppScreen

fun NavGraphBuilder.mainAppNavGraph(rootNavController: NavHostController) {
    navigation(startDestination = "home", route = "mainApp") {
        composable("home") {
            MainAppScreen()
        }
    }
}