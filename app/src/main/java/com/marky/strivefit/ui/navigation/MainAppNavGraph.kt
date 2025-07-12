package com.marky.strivefit.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.marky.strivefit.ui.screens.mainApp.MainAppScreen

fun NavGraphBuilder.MainAppNavGraph(rootNavController: NavHostController) {
    navigation(MainRoutes.Home.route, route = "mainApp") {

        composable(MainRoutes.Home.route) {
            MainAppScreen()
        }
    }
}