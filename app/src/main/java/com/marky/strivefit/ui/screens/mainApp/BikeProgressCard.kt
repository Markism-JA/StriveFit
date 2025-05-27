package com.marky.strivefit.ui.screens.mainApp

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Bike
import com.composables.icons.lucide.Footprints
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pause
import com.marky.strivefit.ui.theme.LocalThemeMode
import com.marky.strivefit.ui.theme.StriveFitTheme
import com.marky.strivefit.ui.theme.ThemeMode
import com.marky.strivefit.ui.utilities.calculateWindowWidthSizeClass
import com.marky.strivefit.ui.viewModel.ThemeManager


@Composable
fun BikeActivityScreen() {

    val darkCardColor = when (LocalThemeMode.current) {
        ThemeMode.LIGHT -> Color(0xFF1C1C1E)
        ThemeMode.DARK -> MaterialTheme.colorScheme.surfaceVariant
        ThemeMode.SYSTEM -> if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surfaceVariant else Color(
            0xFF1C1C1E
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


                Text(
                    text = "Biking",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(24.dp))


                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        progress = 0.65f,
                        strokeWidth = 10.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(200.dp)
                    )
                    Icon(
                        imageVector = Lucide.Bike,
                        contentDescription = "Biking",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))


                Text(
                    text = "Time xx:xx \\ xxxx km",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))


                Icon(
                    imageVector = Lucide.Pause,
                    contentDescription = "Pause",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))


                Text(
                    text = "Next : Running",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))


                Button(
                    onClick = { /* Start action */ },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "Start Now",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

    }
}

@Preview(showBackground = true)
@Composable
fun BikeActivityCardPreview() {
    MaterialTheme {
        BikeActivityScreen()
    }
}

