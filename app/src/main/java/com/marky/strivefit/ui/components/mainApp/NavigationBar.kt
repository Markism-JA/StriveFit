package com.marky.strivefit.ui.components.mainApp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.marky.strivefit.ui.components.mainApp.MainScreens
import com.marky.strivefit.ui.theme.CommonColors
import com.marky.strivefit.ui.theme.LocalThemeMode
import com.marky.strivefit.ui.theme.ThemeMode
import androidx.compose.runtime.getValue

@Composable
fun NavigationBar(
    navController: NavHostController,
    screens: List<MainScreens>,
    modifier: Modifier = Modifier.Companion
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        // Secondary panel/background that will show behind the main surface
        // This creates the appearance of a cutout when combined with the transparent area in the main surface
        Surface(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .align(Alignment.Companion.BottomCenter)
                .height(64.dp),
            color = Color.Companion.Transparent,
            shape = RoundedCornerShape(24.dp)
        ) {}

        // Main navigation surface
        Surface(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .align(Alignment.Companion.BottomCenter)
                .height(64.dp),
            color = when (LocalThemeMode.current) {
                ThemeMode.LIGHT -> CommonColors.bottomNavLight
                ThemeMode.DARK -> CommonColors.bottomNavDark
                ThemeMode.SYSTEM -> if (isSystemInDarkTheme()) CommonColors.bottomNavDark else CommonColors.bottomNavLight
            },
            shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)
        ) {
            Row(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                screens.forEach { screen ->
                    val selected =
                        currentDestination?.hierarchy?.any { it.route == screen.route } == true

                    Box(
                        modifier = Modifier.Companion
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
                        contentAlignment = Alignment.Companion.Center
                    ) {
                        if (!selected) {
                            // Regular icon for unselected items
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.title,
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.Companion.size(24.dp)
                            )
                        } else {
                            // This creates the "hole" in the navigation bar
                            Box(
                                modifier = Modifier.Companion
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(Color.Companion.Transparent)
                            )
                        }
                    }
                }
            }
        }

        // Selected item indicators (the floating buttons)
        Row(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Companion.CenterVertically
        ) {
            screens.forEach { screen ->
                val selected =
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true

                Box(
                    modifier = Modifier.Companion.weight(1f),
                    contentAlignment = Alignment.Companion.Center
                ) {
                    if (selected) {
                        // Circular box matching the app background
                        Box(
                            modifier = Modifier.Companion
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.background)
                        )

                        // Selected Icon
                        Box(
                            modifier = Modifier.Companion
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
                            contentAlignment = Alignment.Companion.Center
                        ) {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.title,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.Companion.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}