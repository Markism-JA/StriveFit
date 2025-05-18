package com.marky.strivefit.ui.viewModel

import FoldPosition
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import kotlinx.coroutines.launch

class ConfigurationViewModel : ViewModel() {
    val windowWidthSizeClass: MutableState<WindowWidthSizeClass> = mutableStateOf(WindowWidthSizeClass.Compact)
    val windowHeightSizeClass: MutableState<WindowHeightSizeClass> = mutableStateOf(WindowHeightSizeClass.Compact)
    val isFolded: MutableState<Boolean> = mutableStateOf(false)
    val foldPosition: MutableState<FoldPosition> = mutableStateOf(FoldPosition.NONE)

    fun updateConfiguration(
        screenWidth: Int,
        screenHeight: Int,
        displayFeatures: List<DisplayFeature>?
    ) {
            viewModelScope.launch {
            windowWidthSizeClass.value = calculateWindowWidthSizeClass(screenWidth.dp)
            windowHeightSizeClass.value = calculateWindowHeightSizeClass(screenHeight.dp)

                // Handle folding features
            val foldingFeature = displayFeatures?.filterIsInstance<FoldingFeature>()?.firstOrNull()
            isFolded.value = foldingFeature?.state == FoldingFeature.State.HALF_OPENED
            foldPosition.value = when {
                foldingFeature == null -> FoldPosition.NONE
                foldingFeature.orientation == FoldingFeature.Orientation.HORIZONTAL -> FoldPosition.HORIZONTAL
                else -> FoldPosition.VERTICAL
            }
        }
    }
    private fun calculateWindowWidthSizeClass(width: Dp): WindowWidthSizeClass {
        return when {
            width < 600.dp -> {
                WindowWidthSizeClass.Compact
            }
            width < 840.dp -> WindowWidthSizeClass.Medium
            else -> WindowWidthSizeClass.Expanded
        }
    }

    private fun calculateWindowHeightSizeClass(height: Dp): WindowHeightSizeClass {
        return WindowHeightSizeClass.Expanded // You can further refine this based on your needs
    }