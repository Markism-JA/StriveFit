package com.marky.strivefit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.marky.strivefit.ui.navigation.StriveFitApp
import com.marky.strivefit.ui.screens.mainApp.MainAppScreen
import com.marky.strivefit.ui.theme.StriveFitTheme
import com.marky.strivefit.ui.theme.ThemeMode
import com.marky.strivefit.ui.viewModel.ThemeManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val themeManager: ThemeManager = hiltViewModel()
            StriveFitTheme {
                StriveFitApp()
            }
//                MainAppScreen(themeManager = themeManager);
        }
    }
}