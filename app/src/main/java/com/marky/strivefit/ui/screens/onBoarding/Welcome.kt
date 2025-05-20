package com.marky.strivefit.ui.screens.onBoarding

import FoldPosition
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.marky.strivefit.ui.components.AnimatedLogo
import com.marky.strivefit.ui.components.AnimatedTextLogo
import com.marky.strivefit.ui.components.LoginPrompt
import com.marky.strivefit.ui.components.animated.AnimatedWelcomeButton
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.unit.Dp
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import kotlinx.coroutines.delay

@Composable
fun Welcome(
    windowSizeClass: WindowSizeClass? = null,
    displayFeature: List<DisplayFeature>? = null,
    onSignUpClick: () -> Unit = {},
    onGuestClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation

    val screeenWidth = configuration.screenWidthDp.dp
    val screeenHeight = configuration.screenHeightDp.dp

    val widthSizeClass by remember(windowSizeClass, screeenWidth) {
        mutableStateOf(windowSizeClass?.widthSizeClass ?: calculateWindowWidthSizeClass(screeenWidth))
    }

    val heightSizeClass by remember(windowSizeClass, screeenHeight) {
        mutableStateOf(windowSizeClass?.heightSizeClass ?: calculateWindowHeightSizeClass(screeenHeight))
    }

    val foldingFeature = displayFeature?.filterIsInstance<FoldingFeature>()?.firstOrNull()
    val isFolded = foldingFeature?.state == FoldingFeature.State.HALF_OPENED
    val foldPosition = when {
        foldingFeature == null -> FoldPosition.NONE
        foldingFeature.orientation == FoldingFeature.Orientation.HORIZONTAL -> FoldPosition.HORIZONTAL
        else -> FoldPosition.VERTICAL
    }

    var animationPlayed by remember { mutableStateOf(false) }
    val animationSequence = remember { mutableStateListOf<Boolean>().apply {
        repeat(4) {
                add(false)
            }
        }
    }

    LaunchedEffect(key1 = true) {
        animationSequence[0] = true
        delay(300)
        animationSequence[1] = true
        delay(300)
        animationSequence[2] = true
        delay(300)
        animationSequence[3] = true
        animationPlayed = true
    }

    val aspectRatio = screeenWidth / screeenHeight

    when {
        isFolded && foldPosition == FoldPosition.HORIZONTAL ->
            landscapeWelcomeLayout(
                animationPlayed = animationPlayed,
                animationSequence = animationSequence,
                widthSizeClass = widthSizeClass,
                heightSizeClass = heightSizeClass,
                onSignUpClick = onSignUpClick,
                onGuestClick = onGuestClick,
                onLoginClick = onLoginClick,
            )

        isFolded && foldPosition == FoldPosition.VERTICAL -> {
            PortraitWelcomeLayout(
                animationPlayed = animationPlayed,
                animationSequence = animationSequence,
                widthSizeClass = widthSizeClass,
                heightSizeClass = heightSizeClass,
                onSignUpClick = onSignUpClick,
                onGuestClick = onGuestClick,
                onLoginClick = onLoginClick,
            )
        }

        aspectRatio > 1.2f && widthSizeClass != WindowWidthSizeClass.Compact -> {
            val isExplicitlyLandscape = orientation == Configuration.ORIENTATION_LANDSCAPE
            if (isExplicitlyLandscape || aspectRatio > 1.5f) {
                landscapeWelcomeLayout(
                    animationPlayed = animationPlayed,
                    animationSequence = animationSequence,
                    widthSizeClass = widthSizeClass,
                    heightSizeClass = heightSizeClass,
                    onSignUpClick = onSignUpClick,
                    onGuestClick = onGuestClick,
                    onLoginClick = onLoginClick
                )
            } else {
                PortraitWelcomeLayout(
                    animationPlayed = animationPlayed,
                    animationSequence = animationSequence,
                    widthSizeClass = widthSizeClass,
                    heightSizeClass = heightSizeClass,
                    onSignUpClick = onSignUpClick,
                    onGuestClick = onGuestClick,
                    onLoginClick = onLoginClick,
                )
            }
        }

        else -> {
            PortraitWelcomeLayout(
                animationPlayed = animationPlayed,
                animationSequence = animationSequence,
                widthSizeClass = widthSizeClass,
                heightSizeClass = heightSizeClass,
                onSignUpClick = onSignUpClick,
                onGuestClick = onGuestClick,
                onLoginClick = onLoginClick,
            )
        }
    }
}

@Composable
private fun PortraitWelcomeLayout(
    animationPlayed: Boolean,
    animationSequence: List<Boolean>,
    widthSizeClass: WindowWidthSizeClass,
    heightSizeClass: WindowHeightSizeClass,
    foldPosition: FoldPosition = FoldPosition.NONE,
    onSignUpClick: () -> Unit,
    onGuestClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    // Adjust logo size based on screen width
    val logoSize = when (widthSizeClass) {
        WindowWidthSizeClass.Expanded -> 240.dp
        WindowWidthSizeClass.Medium -> 200.dp
        else -> 160.dp
    }

    // Adjust text logo width based on screen width
    val textLogoWidth = when (widthSizeClass) {
        WindowWidthSizeClass.Expanded -> 300.dp
        WindowWidthSizeClass.Medium -> 250.dp
        else -> 200.dp
    }

    // Adjust spacing based on screen height
    val topSpacing = when (heightSizeClass) {
        WindowHeightSizeClass.Expanded -> 80.dp
        WindowHeightSizeClass.Medium -> 60.dp
        else -> 40.dp
    }

    val loginSpacing = when (heightSizeClass) {
        WindowHeightSizeClass.Expanded -> 24.dp
        WindowHeightSizeClass.Medium -> 16.dp
        else -> 12.dp
    }

    // Apply special padding for foldable devices in tabletop mode (horizontal fold)
    val columnPadding = if (foldPosition == FoldPosition.HORIZONTAL) {
        // Add extra top/bottom padding to accommodate the fold
        PaddingValues(horizontal = 16.dp, vertical = 24.dp)
    } else {
        PaddingValues(16.dp)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(columnPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (heightSizeClass == WindowHeightSizeClass.Compact) {
            Arrangement.SpaceEvenly // More compact spacing for smaller heights
        } else {
            Arrangement.Center // Default center alignment
        }
    ) {
        AnimatedLogo(
            isAnimationPlayed = animationPlayed,
            animationTriggered = animationSequence.getOrElse(0) { false },
            modifier = Modifier.size(logoSize)
        )

        AnimatedTextLogo(
            isAnimationPlayed = animationPlayed,
            animationTriggered = animationSequence.getOrElse(1) { false },
            modifier = Modifier.width(textLogoWidth).padding(top = 20.dp)
        )

        AnimatedWelcomeButton(
            onSignUpClick = onSignUpClick,
            onGuestClick = onGuestClick,
            animationTriggered = animationSequence.getOrElse(2) { false },
            modifier = Modifier.padding(top = topSpacing),
            widthSizeClass = widthSizeClass
        )

        LoginPrompt(
            onLoginClick = onLoginClick,
            animationTriggered = animationSequence.getOrElse(3) { false },
            modifier = Modifier.padding(top = loginSpacing)
        )
    }
}

@Composable
private fun landscapeWelcomeLayout(
    animationPlayed: Boolean,
    animationSequence: List<Boolean>,
    widthSizeClass: WindowWidthSizeClass,
    heightSizeClass: WindowHeightSizeClass,
    foldPosition: FoldPosition = FoldPosition.NONE,
    onSignUpClick: () -> Unit,
    onGuestClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    // Adjust logo size based on available height
    val logoSize = when (heightSizeClass) {
        WindowHeightSizeClass.Expanded -> 200.dp
        WindowHeightSizeClass.Medium -> 160.dp
        else -> 120.dp
    }

    // Adjust text logo width based on available space
    val textLogoWidth = when (heightSizeClass) {
        WindowHeightSizeClass.Expanded -> 220.dp
        WindowHeightSizeClass.Medium -> 180.dp
        else -> 150.dp
    }

    // Apply special padding for foldable devices in book mode (vertical fold)
    val rowPadding = if (foldPosition == FoldPosition.VERTICAL) {
        // Add extra padding to accommodate the fold
        PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    } else {
        PaddingValues(16.dp)
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(rowPadding),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side - Logos
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            AnimatedLogo(
                isAnimationPlayed = animationPlayed,
                animationTriggered = animationSequence.getOrElse(0) { false },
                modifier = Modifier.size(logoSize)
            )

            AnimatedTextLogo(
                isAnimationPlayed = animationPlayed,
                animationTriggered = animationSequence.getOrElse(1) { false },
                modifier = Modifier.width(textLogoWidth).padding(top = 16.dp)
            )
        }

        // Right side
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {

            AnimatedWelcomeButton(
                onSignUpClick = onSignUpClick,
                onGuestClick = onGuestClick,
                animationTriggered = animationSequence.getOrElse(2) { false },
                widthSizeClass = widthSizeClass
            )

            LoginPrompt(
                onLoginClick = onLoginClick,
                animationTriggered = animationSequence.getOrElse(3) { false },
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

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
