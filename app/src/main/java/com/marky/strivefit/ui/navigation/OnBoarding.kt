package com.marky.strivefit.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavGraph
import androidx.navigation.navigation
import com.marky.strivefit.ui.screens.onBoarding.Welcome
import com.marky.strivefit.ui.screens.onBoarding.Login
import com.marky.strivefit.ui.screens.onBoarding.SignUp

fun NavGraphBuilder.onboardingNavGraph(navController: NavHostController) {
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
            onGuestClick = { navController.navigate("userSetup?origin=guest") }
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
            onSignUpClick = { /* testing try  */ },
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
            onSuccessfulLogin = { /* Handle login logic */ },
            onGoogleLoginClick = { /* Google login */ },
            onSignupClick = { navController.navigate("signup") }
        )
    }

    //calls a different nav graph for user setup
    navigation(
        startDestination = "SetupEntry?origin={origin}",
        route = "userSetup"
    ) {
        userSetupNavGraph(navController)
    }

}