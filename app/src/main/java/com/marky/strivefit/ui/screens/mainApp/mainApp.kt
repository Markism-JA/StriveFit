package com.marky.strivefit.ui.screens.mainApp


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.composables.icons.lucide.*
import com.marky.strivefit.ui.theme.CommonColors
import com.marky.strivefit.ui.theme.LocalThemeMode
import com.marky.strivefit.ui.theme.StriveFitTheme
import com.marky.strivefit.ui.theme.ThemeManager
import com.marky.strivefit.ui.theme.ThemeMode
import com.marky.strivefit.ui.theme.getThemeIconColor

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Lucide.House
    )

    object Workout : BottomBarScreen(
        route = "workout",
        title = "Workout",
        icon = Lucide.ClipboardList
    )

    object Challenges : BottomBarScreen(
        route = "challenges",
        title = "Challenges",
        icon = Lucide.Mountain
    )

    object Stats : BottomBarScreen(
        route = "stats",
        title = "Stats",
        icon = Lucide.ChartColumnIncreasing
    )
}

data class QuickActionItem(
    val icon: ImageVector,
    val label: String,
    val onClick: () -> Unit
)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onThemeChanged: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentThemeMode = LocalThemeMode.current

    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
        ),
        label = "Theme Icon Scale"
    )

    val iconColor = getThemeIconColor(currentThemeMode)

    // Define the theme icon based on current mode
    val themeIcon = when (currentThemeMode) {
        ThemeMode.LIGHT -> Lucide.Sun
        ThemeMode.DARK -> Lucide.Moon
        ThemeMode.SYSTEM -> Lucide.Box
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        },
        actions = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.15f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        // Update local state first
                        val nextThemeMode = when (currentThemeMode) {
                            ThemeMode.LIGHT -> ThemeMode.DARK
                            ThemeMode.DARK -> ThemeMode.SYSTEM
                            ThemeMode.SYSTEM -> ThemeMode.LIGHT
                        }
                        // Then notify parent with callback
                        onThemeChanged(nextThemeMode)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = themeIcon,
                    contentDescription = "Toggle theme mode",
                    tint = iconColor,
                    modifier = Modifier
                        .size(24.dp)
                        .scale(scale)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Lucide.Settings,
                    contentDescription = "Settings",
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = onProfileClick) {
                Icon(
                    imageVector = Lucide.User,
                    contentDescription = "Profile",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground
        ),
        windowInsets = WindowInsets(0, 0, 0, 0),
        modifier = modifier
    )
}

@Composable
fun MainAppScreen() {
    val navController = rememberNavController()
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Workout,
        BottomBarScreen.Challenges,
        BottomBarScreen.Stats
    )

    var currentScreenTitle by remember { mutableStateOf("Home") }
    val themeManager = remember { ThemeManager() }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isHomeSelected = currentRoute == BottomBarScreen.Home.route

    StriveFitTheme(themeManager = themeManager) {
        Scaffold(
            topBar = {
                TopBar(
                    title = currentScreenTitle,
                    onProfileClick = { /* Handle profile click */ },
                    onSettingsClick = { /* Handle settings click */ },
                    onThemeChanged = { newThemeMode ->
                        themeManager.setThemeMode(newThemeMode)
                    }
                )
            },
            bottomBar = {
                NavigationBar(
                    navController = navController,
                    screens = screens
                )
            },
            // Remove default window insets for the Scaffold
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { innerPadding ->
            Box(Modifier.fillMaxSize()) {
                Box(Modifier.padding(innerPadding)) {
                    NavHost(
                        navController = navController,
                        startDestination = BottomBarScreen.Home.route,
                        enterTransition = {
                            EnterTransition.None
                        },
                        exitTransition = {
                            ExitTransition.None
                        }
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
                    }
                }

                // Only show FAB on home screen
                if (isHomeSelected) {
                    QuickActionFab(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 80.dp, end = 16.dp)
                    )
                }
            }
        }
    }
}

// Used by ReVanced-style animation
private fun <T> NavBackStackEntry.compareRoutes(other: NavBackStackEntry, routeOrder: List<T>): Int
        where T : Enum<T>, T : ScreenDestination {
    val thisRoute = this.destination.route ?: return 0
    val otherRoute = other.destination.route ?: return 0

    val thisIndex = routeOrder.indexOfFirst { it.route == thisRoute }
    val otherIndex = routeOrder.indexOfFirst { it.route == otherRoute }

    return thisIndex.compareTo(otherIndex)
}

// Interface to ensure screen destinations have a route
interface ScreenDestination {
    val route: String
}

@Composable
fun QuickActionFab(
    modifier: Modifier = Modifier
) {
    var isFabExpanded by remember { mutableStateOf(false) }

    val quickActions = listOf(
        QuickActionItem(
            icon = Lucide.Bike,
            label = "Bike",
            onClick = { /* Handle bike action */ }
        ),
        QuickActionItem(
            icon = Lucide.Footprints,
            label = "Run",
            onClick = { /* Handle run action */ }
        ),
        QuickActionItem(
            icon = Lucide.BicepsFlexed,
            label = "Exercise",
            onClick = { /* Handle exercise action */ }
        )
    )

    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
    ) {
        // Show sub-menu items when expanded
        AnimatedVisibility(
            visible = isFabExpanded,
            enter = fadeIn() + expandVertically(expandFrom = Alignment.Bottom) + scaleIn(initialScale = 0.8f),
            exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Bottom) + scaleOut()
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                quickActions.forEach { action ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            action.onClick()
                            isFabExpanded = false
                        }
                    ) {
                        // Action label
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.shadow(4.dp, RoundedCornerShape(16.dp))
                        ) {
                            Text(
                                text = action.label,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Action icon
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(48.dp)
                                .shadow(4.dp, CircleShape)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Icon(
                                imageVector = action.icon,
                                contentDescription = action.label,
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }

        // Main FAB
        val transition = updateTransition(targetState = isFabExpanded, label = "FAB Rotation")
        val rotation by transition.animateFloat(
            label = "FAB Rotation",
            transitionSpec = { tween(durationMillis = 300, easing = LinearOutSlowInEasing) }
        ) { expanded -> if (expanded) 45f else 0f }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(56.dp)
                .shadow(6.dp, CircleShape)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    isFabExpanded = !isFabExpanded
                }
        ) {
            Icon(
                imageVector = Lucide.Dumbbell,
                contentDescription = "Quick Actions",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(24.dp)
                    .rotate(rotation)
            )
        }
    }
}

@Composable
fun PlaceholderScreen(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun NavigationBar(
    navController: NavHostController,
    screens: List<BottomBarScreen>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(64.dp),
            color = when (LocalThemeMode.current){
                ThemeMode.LIGHT -> CommonColors.bottomNavLight
                ThemeMode.DARK -> CommonColors.bottomNavDark
                ThemeMode.SYSTEM -> if (isSystemInDarkTheme()) CommonColors.bottomNavDark else CommonColors.bottomNavLight
            },
            shape = RoundedCornerShape(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                screens.forEach { screen ->
                    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                enabled = !selected,
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (!selected) {
                            // Regular icon for unselected items
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.title,
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            // This creates the "hole" in the navigation bar
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(Color.Transparent)
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            screens.forEach { screen ->
                val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    if (selected) {
                        // Circular box matching the app background
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.background)
                        )

                        // Selected Icon
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id)
                                        launchSingleTop = true
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.title,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainAppScreenPreview() {
    MaterialTheme {
        MainAppScreen()
    }
}
