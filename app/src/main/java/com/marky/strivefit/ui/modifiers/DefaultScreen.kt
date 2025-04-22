package com.marky.strivefit.ui.modifiers

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object ScreenModifiers {
    @Composable
    fun Modifier.defaultScreen(): Modifier {

        return Modifier
            .fillMaxSize()
            .padding(16.dp)
    }
}


object ScreenAlignments {
    val centerColumn = Pair(
        Alignment.CenterHorizontally,
        Arrangement.Center
    )
}



