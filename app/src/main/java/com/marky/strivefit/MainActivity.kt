// MainActivity.kt
package com.marky.strivefit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import com.marky.strivefit.ui.screens.Welcome
import com.marky.strivefit.ui.screens.auth.SignUp
import com.marky.strivefit.ui.screens.auth.Login
import com.marky.strivefit.ui.theme.DefaultDark
import com.marky.strivefit.ui.theme.StriveFitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make the system UI draw behind the app
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            StriveFitTheme {
                // Detect current theme
                val isDarkTheme = isSystemInDarkTheme()

                // Configure status bar based on theme
                SideEffect {
                    // Set status bar color transparent
                    window.statusBarColor = Color.Transparent.toArgb()

                    val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
                    windowInsetsController.isAppearanceLightStatusBars = !isDarkTheme
                }

                StriveFitApp(isDarkTheme)
            }
        }
    }
}

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
    // Animation duration settings
    val animationDuration = 400

    // Create separate specs for different animation types
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
                onGoogleLoginClick = { /* Google login */ }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StriveFitTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Welcome()
        }
    }
}