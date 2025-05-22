package com.marky.strivefit.ui.screens.mainApp

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface // Used for the minimal collapsed bar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Box // Theme icon
import com.composables.icons.lucide.ChevronsDown
import com.composables.icons.lucide.ChevronsUp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Moon
import com.composables.icons.lucide.Settings
import com.composables.icons.lucide.Sun
import com.composables.icons.lucide.User
import com.marky.strivefit.ui.theme.ThemeMode
import com.marky.strivefit.ui.theme.getThemeIconColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarContainer( // Renamed to reflect it's a container
    title: String,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onThemeChanged: (ThemeMode) -> Unit,
    currentThemeMode: ThemeMode,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    var isExpandedInLandscape by remember(isLandscape) {
        // Reset expansion state if orientation changes,
        // or if not landscape, it should effectively be "expanded"
        mutableStateOf(if (isLandscape) true else true)
    }

    // Determine if the full TopAppBar should be shown
    val showFullTopBar = if (isLandscape) isExpandedInLandscape else true

    Column(modifier = modifier.fillMaxWidth()) { // Use a Column to stack the TopAppBar or the collapsed view
        AnimatedVisibility(
            visible = showFullTopBar,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            ActualTopBar(
                title = title,
                onProfileClick = onProfileClick,
                onSettingsClick = onSettingsClick,
                onThemeChanged = onThemeChanged,
                currentThemeMode = currentThemeMode,
                isLandscape = isLandscape,
                isExpandedInLandscape = isExpandedInLandscape,
                onToggleExpand = { isExpandedInLandscape = !isExpandedInLandscape }
            )
        }

        // Show a minimal "expand" button strip if in landscape and collapsed
        if (isLandscape && !isExpandedInLandscape) {
            MinimalExpandBar(
                onClick = { isExpandedInLandscape = true },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActualTopBar(
    title: String,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onThemeChanged: (ThemeMode) -> Unit,
    currentThemeMode: ThemeMode,
    isLandscape: Boolean,
    isExpandedInLandscape: Boolean, // This is now specifically for the content within the TopAppBar
    onToggleExpand: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        // Scale animation is for the theme icon itself
        targetValue = 1f, // Theme icon doesn't need to scale based on top bar expansion anymore
        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing),
        label = "Theme Icon Scale"
    )

    val iconColor = getThemeIconColor(currentThemeMode)

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
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        },
        navigationIcon = {
            if (isLandscape) {
                IconButton(onClick = onToggleExpand) { // This now triggers the whole TopBar visibility
                    Icon(
                        imageVector = if (isExpandedInLandscape) Lucide.ChevronsUp else Lucide.ChevronsDown,
                        contentDescription = if (isExpandedInLandscape) "Collapse Top Bar" else "Expand Top Bar",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
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
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground
        ),
        windowInsets = TopAppBarDefaults.windowInsets,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun MinimalExpandBar(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface( // Use Surface for theming and elevation if needed
        modifier = modifier
            .height(60.dp) // Small, consistent height for the collapsed bar
            .clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.background, // Slightly transparent or distinct
        // elevation = 2.dp // Optional shadow
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start // Or Center
        ) {
            Icon(
                imageVector = Lucide.ChevronsDown,
                contentDescription = "Expand Top Bar",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
            // Optionally, you could add a very short title or hint here
            // Text(" Menu", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}