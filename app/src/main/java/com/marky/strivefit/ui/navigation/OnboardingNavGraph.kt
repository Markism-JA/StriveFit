package com.marky.strivefit.ui.navigation

import androidx.compose.animation.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.marky.strivefit.ui.navigation.TransitionAnimation.fadeInSpec
import com.marky.strivefit.ui.navigation.TransitionAnimation.fadeOutSpec
import com.marky.strivefit.ui.navigation.TransitionAnimation.slideAnimSpec
import com.marky.strivefit.ui.screens.onBoaording.SignUpScreen
import com.marky.strivefit.ui.screens.onBoarding.Entry
import com.marky.strivefit.ui.screens.onBoarding.Login
import com.marky.strivefit.ui.screens.onBoarding.WelcomeScreen
import com.marky.strivefit.ui.navigation.PostAuthWelcome

fun NavGraphBuilder.OnboardingNavGraph(navController: NavHostController) {
    navigation<OnboardingGraph>(
        startDestination = Welcome,
    ) {
        composable<Welcome>(
            enterTransition = { fadeIn(animationSpec = fadeInSpec) },
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
            WelcomeScreen(
                onNavigateToSignUp = { navController.navigate(SignUp) },
                onNavigateToLogin = { navController.navigate(Login) },
                onNavigateToGuest = { navController.navigate(GuestLoading) }
            )
        }

        composable<SignUp>(
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
            // This screen no longer has an onSignUpSuccess callback.
            // It relies on the AuthViewModel to change the global auth state.
            SignUpScreen(
                onBackClick = { navController.popBackStack() },
                onSignUpSuccess = { userName ->
                    navController.navigate(PostAuthWelcome(userName = userName))
                }
            )
        }

        composable<Login>(
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
                onSuccessfulLogin = { userName ->
                    navController.navigate(PostAuthWelcome(userName = userName))
                },
                onSignupClick = { navController.navigate(SignUp) }
            )
        }

        composable<GuestLoading> {
            Entry(
                entryType = com.marky.strivefit.ui.screens.onBoarding.EntryType.GUEST,
                onContinueClick = { navController.navigate(UserSetup) }
            )
        }

        composable<PostAuthWelcome> {
            val args = it.toRoute<PostAuthWelcome>()
            Entry(
                entryType = com.marky.strivefit.ui.screens.onBoarding.EntryType.LOGIN,
                userName = args.userName,
                onContinueClick = { navController.navigate(UserSetup) }
            )
        }
    }
}