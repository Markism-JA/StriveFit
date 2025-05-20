package com.marky.strivefit.ui.utilities

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun calculateWindowWidthSizeClass(width: Dp): WindowWidthSizeClass {
    return when {
        width < 600.dp -> WindowWidthSizeClass.Compact
        width < 840.dp -> WindowWidthSizeClass.Medium
        else -> WindowWidthSizeClass.Expanded
    }
}

fun calculateWindowHeightSizeClass(height: Dp): WindowHeightSizeClass {
    return when {
        else -> WindowHeightSizeClass.Expanded
    }
}
