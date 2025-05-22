package com.marky.strivefit.ui.components.mainApp

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Box
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Moon
import com.composables.icons.lucide.Settings
import com.composables.icons.lucide.Sun
import com.composables.icons.lucide.User
import com.marky.strivefit.ui.theme.ThemeMode
import com.marky.strivefit.ui.theme.getThemeIconColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onThemeChanged: (ThemeMode) -> Unit,
    currentThemeMode: ThemeMode,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Determine if the TopAppBar should be visible for the custom AnimatedVisibility.
    // This logic applies when in landscape and a scrollBehavior is active.
    // For ExitUntilCollapsedScrollBehavior, collapsedFraction goes from 0.0 (expanded) to 1.0 (fully collapsed/hidden).
    // We want the custom animation to hide when the bar is fully collapsed.
    val isCustomAnimationVisible = if (isLandscape && scrollBehavior != null) {
        scrollBehavior.state.collapsedFraction < 1.0f
    } else {
        true
    }

    AnimatedVisibility(
        visible = isCustomAnimationVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 200)) + expandVertically(animationSpec = tween(durationMillis = 300)),
        exit = fadeOut(animationSpec = tween(durationMillis = 200)) + shrinkVertically(animationSpec = tween(durationMillis = 300)),
        modifier = modifier // Applies padding from MainAppScreen
    ) {
        ActualTopBarInternal(
            title = title,
            onProfileClick = onProfileClick,
            onSettingsClick = onSettingsClick,
            onThemeChanged = onThemeChanged,
            currentThemeMode = currentThemeMode,
            scrollBehavior = scrollBehavior // Pass the scroll behavior for M3 effects
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActualTopBarInternal(
    title: String,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onThemeChanged: (ThemeMode) -> Unit,
    currentThemeMode: ThemeMode,
    scrollBehavior: TopAppBarScrollBehavior?,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing),
        label = "Theme Icon Scale"
    )

    val iconColor = getThemeIconColor(currentThemeMode)
    val themeIcon = when (currentThemeMode) {
        ThemeMode.LIGHT -> Lucide.Sun
        ThemeMode.DARK -> Lucide.Moon
        ThemeMode.SYSTEM -> Lucide.Box
    }
    val containerBgColor = MaterialTheme.colorScheme.background

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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(iconColor.copy(alpha = 0.15f))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            val nextThemeMode = when (currentThemeMode) {
                                ThemeMode.LIGHT -> ThemeMode.DARK
                                ThemeMode.DARK -> ThemeMode.SYSTEM
                                ThemeMode.SYSTEM -> ThemeMode.LIGHT
                            }
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
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerBgColor,
            scrolledContainerColor = containerBgColor,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground
        ),
        windowInsets = TopAppBarDefaults.windowInsets,
        scrollBehavior = scrollBehavior,
        modifier = modifier.fillMaxWidth()
    )
}