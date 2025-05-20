package com.marky.strivefit.ui.screens.mainApp

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
    modifier: Modifier = Modifier.Companion
) {
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
        ),
        label = "Theme Icon Scale"
    )

    val iconColor = getThemeIconColor(currentThemeMode)

    // Define the theme icon based on current mode
    val themeIcon = when (currentThemeMode) {
        ThemeMode.LIGHT -> Lucide.Sun
        ThemeMode.DARK -> Lucide.Moon
        ThemeMode.SYSTEM -> Lucide.Box
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Companion.Ellipsis,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Companion.Bold)
            )
        },
        actions = {
            Box(
                modifier = Modifier.Companion
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.15f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        // Update local state first
                        val nextThemeMode = when (currentThemeMode) {
                            ThemeMode.LIGHT -> ThemeMode.DARK
                            ThemeMode.DARK -> ThemeMode.SYSTEM
                            ThemeMode.SYSTEM -> ThemeMode.LIGHT
                        }
                        // Then notify parent with callback
                        onThemeChanged(nextThemeMode)
                    },
                contentAlignment = Alignment.Companion.Center
            ) {
                Icon(
                    imageVector = themeIcon,
                    contentDescription = "Toggle theme mode",
                    tint = iconColor,
                    modifier = Modifier.Companion
                        .size(24.dp)
                        .scale(scale)
                )
            }

            Spacer(modifier = Modifier.Companion.width(8.dp))

            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Lucide.Settings,
                    contentDescription = "Settings",
                    modifier = Modifier.Companion.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.Companion.width(8.dp))

            IconButton(onClick = onProfileClick) {
                Icon(
                    imageVector = Lucide.User,
                    contentDescription = "Profile",
                    modifier = Modifier.Companion.size(24.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground
        ),
        windowInsets = TopAppBarDefaults.windowInsets,
        modifier = modifier
    )
}