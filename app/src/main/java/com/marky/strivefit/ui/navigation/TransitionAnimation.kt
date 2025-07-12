package com.marky.strivefit.ui.navigation

import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.IntOffset

object TransitionAnimation {
    val animationDuration = 400

    val slideAnimSpec = tween<IntOffset>(
        durationMillis = animationDuration,
        easing = FastOutSlowInEasing
    )

    val fadeInSpec = tween<Float>(
        durationMillis = animationDuration - 50,
        easing = LinearEasing
    )

    val fadeOutSpec = tween<Float>(
        durationMillis = animationDuration - 100,
        easing = EaseOut
    )
}