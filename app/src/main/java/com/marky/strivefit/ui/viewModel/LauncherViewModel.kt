package com.marky.strivefit.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(
    //to be added later
) : ViewModel() {
    private val _launcherUiState = MutableStateFlow<LauncherUiState>(LauncherUiState.Loading)
    val uiState: StateFlow<LauncherUiState> = _launcherUiState
    init{
        viewModelScope.launch {
            delay(500)
        }
    }
}

sealed class LauncherUiState {
    object Loading : LauncherUiState()
    object AuthRequired : LauncherUiState()
    object SetupRequired : LauncherUiState()
    object Ready : LauncherUiState()
}
