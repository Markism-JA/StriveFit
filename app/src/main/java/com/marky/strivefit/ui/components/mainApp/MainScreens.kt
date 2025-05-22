package com.marky.strivefit.ui.components.mainApp

import androidx.compose.ui.graphics.vector.ImageVector
import com.composables.icons.lucide.ChartColumnIncreasing
import com.composables.icons.lucide.ClipboardList
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Mountain

sealed class MainScreens(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : MainScreens(
        route = "home",
        title = "Home",
        icon = Lucide.House
    )

    object Workout : MainScreens(
        route = "workout",
        title = "Workout",
        icon = Lucide.ClipboardList
    )

    object Challenges : MainScreens(
        route = "challenges",
        title = "Challenges",
        icon = Lucide.Mountain
    )

    object Stats : MainScreens(
        route = "stats",
        title = "Stats",
        icon = Lucide.ChartColumnIncreasing
    )
}