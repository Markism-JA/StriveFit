package com.marky.strivefit.ui.screens.mainApp

import androidx.compose.ui.graphics.vector.ImageVector

data class QuickActionItem(
    val icon: ImageVector,
    val label: String,
    val onClick: () -> Unit
)