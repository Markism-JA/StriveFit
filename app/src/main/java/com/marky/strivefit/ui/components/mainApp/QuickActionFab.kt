package com.marky.strivefit.ui.components.mainApp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.BicepsFlexed
import com.composables.icons.lucide.Bike
import com.composables.icons.lucide.Dumbbell
import com.composables.icons.lucide.Footprints
import com.composables.icons.lucide.Lucide
import com.marky.strivefit.ui.components.mainApp.QuickActionItem
import androidx.compose.runtime.setValue

@Composable
fun QuickActionFab(
    modifier: Modifier = Modifier.Companion
) {
    var isFabExpanded by remember { mutableStateOf(false) }

    val quickActions = listOf(
        QuickActionItem(
            icon = Lucide.Bike,
            label = "Bike",
            onClick = { /* Handle bike action */ }
        ),
        QuickActionItem(
            icon = Lucide.Footprints,
            label = "Run",
            onClick = { /* Handle run action */ }
        ),
        QuickActionItem(
            icon = Lucide.BicepsFlexed,
            label = "Exercise",
            onClick = { /* Handle exercise action */ }
        )
    )

    Column(
        horizontalAlignment = Alignment.Companion.End,
        modifier = modifier
    ) {
        // Show sub-menu items when expanded
        AnimatedVisibility(
            visible = isFabExpanded,
            enter = fadeIn() + expandVertically(expandFrom = Alignment.Companion.Bottom) + scaleIn(
                initialScale = 0.8f
            ),
            exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Companion.Bottom) + scaleOut()
        ) {
            Column(
                horizontalAlignment = Alignment.Companion.End,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.Companion.padding(bottom = 16.dp)
            ) {
                quickActions.forEach { action ->
                    Row(
                        verticalAlignment = Alignment.Companion.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.Companion.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            action.onClick()
                            isFabExpanded = false
                        }
                    ) {
                        // Action label
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.Companion.shadow(
                                4.dp,
                                androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                            )
                        ) {
                            Text(
                                text = action.label,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.Companion.padding(
                                    horizontal = 12.dp,
                                    vertical = 8.dp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.Companion.width(8.dp))

                        // Action icon
                        Box(
                            contentAlignment = Alignment.Companion.Center,
                            modifier = Modifier.Companion
                                .size(48.dp)
                                .shadow(4.dp, CircleShape)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Icon(
                                imageVector = action.icon,
                                contentDescription = action.label,
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.Companion.size(24.dp)
                            )
                        }
                    }
                }
            }
        }

        // Main FAB
        val transition = updateTransition(targetState = isFabExpanded, label = "FAB Rotation")
        val rotation by transition.animateFloat(
            label = "FAB Rotation",
            transitionSpec = { tween(durationMillis = 300, easing = LinearOutSlowInEasing) }
        ) { expanded -> if (expanded) 45f else 0f }

        Box(
            contentAlignment = Alignment.Companion.Center,
            modifier = Modifier.Companion
                .size(56.dp)
                .shadow(6.dp, CircleShape)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    isFabExpanded = !isFabExpanded
                }
        ) {
            Icon(
                imageVector = Lucide.Dumbbell,
                contentDescription = "Quick Actions",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.Companion
                    .size(24.dp)
                    .rotate(rotation)
            )
        }
    }
}