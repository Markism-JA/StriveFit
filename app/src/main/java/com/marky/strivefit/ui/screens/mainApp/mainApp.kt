package com.marky.strivefit.ui.screens.mainApp

import android.content.res.Configuration // Make sure this is imported
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior // For type hint
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.marky.strivefit.ui.navigation.MainRoutes
import com.marky.strivefit.ui.components.mainApp.NavigationBar
import com.marky.strivefit.ui.components.mainApp.QuickActionFab
import com.marky.strivefit.ui.components.mainApp.TopBar
import com.marky.strivefit.ui.theme.StriveFitTheme
import com.marky.strivefit.ui.utilities.calculateWindowWidthSizeClass
import com.marky.strivefit.ui.viewModel.ThemeManagerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(
) {
    val themeManager: ThemeManagerViewModel = hiltViewModel()
    val currentThemeMode = themeManager.themeMode.collectAsState().value
    val navController = rememberNavController()
    val screens = listOf(
        MainRoutes.Home,
        MainRoutes.Workout,
        MainRoutes.Challenges,
        MainRoutes.Stats
    )

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val screenWidthDp = configuration.screenWidthDp.dp
    val widthSizeClass = calculateWindowWidthSizeClass(screenWidthDp)

    val horizontalContentPadding = when (widthSizeClass) {
        WindowWidthSizeClass.Compact -> 0.dp
        WindowWidthSizeClass.Medium -> 48.dp
        WindowWidthSizeClass.Expanded -> 80.dp
        else -> 24.dp
    }

    // Conditionally create and use scroll behavior for landscape only
    val topAppBarScrollBehavior: TopAppBarScrollBehavior? = if (isLandscape) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    } else {
        null // No scroll behavior in portrait, TopAppBar will be static
    }

    StriveFitTheme {
        var currentScreenTitle by remember { mutableStateOf("Home") }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val isHomeSelected = currentRoute == MainRoutes.Home.route

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier.widthIn(max = 840.dp)
            ) {
                Scaffold(
                    modifier = if (isLandscape && topAppBarScrollBehavior != null) {
                        Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
                    } else {
                        Modifier
                    },
                    topBar = {
                        TopBar(
                            title = currentScreenTitle,
                            onProfileClick = { /* Handle profile click */ },
                            onSettingsClick = { /* Handle settings click */ },
                            onThemeChanged = { newThemeMode ->
                                themeManager.SetThemeMode(newThemeMode)
                            },
                            currentThemeMode = currentThemeMode,
                            scrollBehavior = topAppBarScrollBehavior, // Will be null in portrait
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
                            .then(
                                if (isLandscape) {
                                    Modifier.windowInsetsPadding(WindowInsets.statusBars)
                                } else {
                                    Modifier
                                }
                            )
                            .padding(horizontal = horizontalContentPadding)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = MainRoutes.Home.route,
                            enterTransition = { EnterTransition.None },
                            exitTransition = { ExitTransition.None }
                        ) {
                            composable(MainRoutes.Home.route) { currentScreenTitle = "Home"; HomeScreen() }
                            composable(MainRoutes.Workout.route) { currentScreenTitle = "Workout"; WorkoutScreen() }
                            composable(MainRoutes.Challenges.route) { currentScreenTitle = "Challenges"; ChallengesScreen() }
                            composable(MainRoutes.Stats.route) { currentScreenTitle = "Stats"; StatsScreen() }
                        }
                    }
                }
            }
        }

        if (isHomeSelected) {
            Box(modifier = Modifier.fillMaxSize()) {
                QuickActionFab(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 80.dp, end = (16.dp + horizontalContentPadding))
                )
            }
        }
    }
}