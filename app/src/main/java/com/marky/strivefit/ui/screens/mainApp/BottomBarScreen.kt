package com.marky.strivefit.ui.screens.mainApp

import androidx.compose.ui.graphics.vector.ImageVector
import com.composables.icons.lucide.ChartColumnIncreasing
import com.composables.icons.lucide.ClipboardList
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Mountain

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