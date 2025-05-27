package com.marky.strivefit.ui.screens.mainApp

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.marky.strivefit.ui.theme.StriveFitTheme
import com.marky.strivefit.ui.utilities.calculateWindowWidthSizeClass
import com.marky.strivefit.ui.viewModel.ThemeManager

@Composable
fun MainAppScreen(
) {
    val themeManager: ThemeManager = hiltViewModel()
    val currentThemeMode = themeManager.themeMode.collectAsState().value
    val navController = rememberNavController()
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Workout,
        BottomBarScreen.Challenges,
        BottomBarScreen.Stats
    )

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val widthSizeClass = calculateWindowWidthSizeClass(screenWidthDp)

    val horizontalContentPadding = when (widthSizeClass) {
        WindowWidthSizeClass.Compact -> 0.dp
        WindowWidthSizeClass.Medium -> 48.dp
        WindowWidthSizeClass.Expanded -> 80.dp
        else -> 24.dp
    }

    StriveFitTheme {
        var currentScreenTitle by remember { mutableStateOf("Home") }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val isHomeSelected = currentRoute == BottomBarScreen.Home.route

        // Main container box that will center content on larger screens
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier.widthIn(max = 840.dp)
            ) {
                Scaffold(
                    topBar = {
                        TopBarContainer(
                            title = currentScreenTitle,
                            onProfileClick = { /* Handle profile click */ },
                            onSettingsClick = { /* Handle settings click */ },
                            onThemeChanged = { newThemeMode ->
                                themeManager.SetThemeMode(newThemeMode)
                            },
                            currentThemeMode = currentThemeMode,
                            modifier = Modifier.padding(horizontal = horizontalContentPadding)
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            navController = navController,
                            screens = screens,
                            modifier = Modifier.padding(horizontal = horizontalContentPadding)
                        )
                    },
                    contentWindowInsets = WindowInsets(0, 0, 0, 0)
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .padding(horizontal = horizontalContentPadding)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = BottomBarScreen.Home.route,
                            enterTransition = { EnterTransition.None },
                            exitTransition = { ExitTransition.None }
                        ) {
                            composable(
                                route = BottomBarScreen.Home.route,
                                enterTransition = { EnterTransition.None },
                                exitTransition = { ExitTransition.None }
                            ) {
                                currentScreenTitle = "Home"
                                HomeScreen()
                            }
                            composable(
                                route = BottomBarScreen.Workout.route,
                                enterTransition = { EnterTransition.None },
                                exitTransition = { ExitTransition.None }
                            ) {
                                currentScreenTitle = "Workout"
                                WorkoutScreen()
                            }
                            composable(
                                route = BottomBarScreen.Challenges.route,
                                enterTransition = { EnterTransition.None },
                                exitTransition = { ExitTransition.None }
                            ) {
                                currentScreenTitle = "Challenges"
                                ChallengesScreen()
                            }
                            composable(
                                route = BottomBarScreen.Stats.route,
                                enterTransition = { EnterTransition.None },
                                exitTransition = { ExitTransition.None }
                            ) {
                                currentScreenTitle = "Stats"
                                StatsScreen()
                            }
                            composable("run_activity") {
                                currentScreenTitle = "Running"
                                RunningActivityScreen()
                            }
                            composable("exercise_activity") {
                                currentScreenTitle = "Exercising"
                                ExerciseActivityScreen(
                                    onStartClick = { navController.navigate("bike_activity") }
                                )
                            }


                            composable("bike_activity") {
                                currentScreenTitle = "Biking"
                                BikeActivityScreen()
                            }
                        }
                    }
                }
            }
        }

        // FAB positioned outside the above structure to ensure proper positioning
        if (isHomeSelected) {
            Box(modifier = Modifier.fillMaxSize()) {
                QuickActionFab(
                    navController = navController,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 80.dp, end = (16.dp + horizontalContentPadding))
                )
            }
        }
    }
}