package com.marky.strivefit.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marky.strivefit.ui.screens.Welcome
import com.marky.strivefit.ui.screens.auth.Login
import com.marky.strivefit.ui.screens.auth.SignUp
import com.marky.strivefit.ui.theme.DefaultDark


@Composable
fun StriveFitApp(isDarkTheme: Boolean) {
    val navController = rememberNavController()
    val backgroundColor = if (isDarkTheme) DefaultDark.background else MaterialTheme.colorScheme.background

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
            AppNavigation(navController)
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    val animationDuration = 400

    val slideAnimSpec = tween<IntOffset>(
        durationMillis = animationDuration,
        easing = FastOutSlowInEasing
    )

    val fadeInSpec = tween<Float>(
        durationMillis = animationDuration - 50,
        easing = LinearEasing
    )

    val fadeOutSpec = tween<Float>(
        durationMillis = animationDuration - 100,
        easing = EaseOut
    )

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable(
            route = "welcome",
            enterTransition = {
                fadeIn(animationSpec = fadeInSpec)
            },
            exitTransition = {
                fadeOut(animationSpec = fadeOutSpec) +
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Start,
                            animationSpec = slideAnimSpec
                        )
            },
            popEnterTransition = {
                fadeIn(animationSpec = fadeInSpec) +
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.End,
                            animationSpec = slideAnimSpec
                        )
            }
        ) {
            Welcome(
                onSignUpClick = { navController.navigate("signup") },
                onLoginClick = { navController.navigate("login") },
                onGuestClick = { /* navController.navigate("guest") */ }
            )
        }

        composable(
            route = "signup",
            enterTransition = {
                fadeIn(animationSpec = fadeInSpec) +
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Start,
                            animationSpec = slideAnimSpec
                        )
            },
            exitTransition = {
                fadeOut(animationSpec = fadeOutSpec) +
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.End,
                            animationSpec = slideAnimSpec
                        )
            }
        ) {
            SignUp(
                onBackClick = { navController.popBackStack() },
                onSignUpClick = { /* Handle sign up logic */ },
                onGoogleSignUpClick = { /* Google sign up */ }
            )
        }

        composable(
            route = "login",
            enterTransition = {
                fadeIn(animationSpec = fadeInSpec) +
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Start,
                            animationSpec = slideAnimSpec
                        )
            },
            exitTransition = {
                fadeOut(animationSpec = fadeOutSpec) +
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.End,
                            animationSpec = slideAnimSpec
                        )
            }
        ) {
            Login(
                onBackClick = { navController.popBackStack() },
                onLoginClick = { /* Handle login logic */ },
                onGoogleLoginClick = { /* Google login */ },
                onSignupClick = { navController.navigate("signup") }
            )
        }
    }
}