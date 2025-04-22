package com.marky.strivefit.ui.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class AppDestination(val route: String) {
    object Welcome : AppDestination("welcome")
    object UserSetup : AppDestination("userSetupHome")
    object MainApp : AppDestination("mainHome")
}

class AppFlowViewModel : ViewModel() {
    private val _startDestination = MutableStateFlow<AppDestination>(AppDestination.Welcome)
    val startDestination: StateFlow<AppDestination> = _startDestination

    fun evaluateAppStart(
        isUserLoggedIn: Boolean,
        isUserSetupComplete: Boolean
    ) {
        _startDestination.value = when {
            !isUserLoggedIn -> AppDestination.Welcome
            !isUserSetupComplete -> AppDestination.UserSetup
            else -> AppDestination.MainApp
        }
    }

    fun markUserAsSetupComplete() {
        _startDestination.value = AppDestination.MainApp
    }
}



