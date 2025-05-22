package com.marky.strivefit.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.composables.icons.lucide.ChartColumnIncreasing
import com.composables.icons.lucide.ClipboardList
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Mountain

sealed class MainRoutes(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : MainRoutes(
        route = "home",
        title = "Home",
        icon = Lucide.House
    )

    object Workout : MainRoutes(
        route = "workout",
        title = "Workout",
        icon = Lucide.ClipboardList
    )

    object Challenges : MainRoutes(
        route = "challenges",
        title = "Challenges",
        icon = Lucide.Mountain
    )

    object Stats : MainRoutes(
        route = "stats",
        title = "Stats",
        icon = Lucide.ChartColumnIncreasing
    )
}