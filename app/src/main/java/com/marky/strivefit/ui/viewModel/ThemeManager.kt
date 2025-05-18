package com.marky.strivefit.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.marky.strivefit.ui.theme.ThemeColorOption
import com.marky.strivefit.ui.theme.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemeManager : ViewModel() {
    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode

    private val _colorOption = MutableStateFlow(ThemeColorOption.DEFAULT)
    val colorOption: StateFlow<ThemeColorOption> =_colorOption

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }

    fun setColorOption(option: ThemeColorOption) {
        _colorOption.value = option
    }
}
