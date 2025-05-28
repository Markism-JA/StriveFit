package com.marky.strivefit.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavOrigin @Inject constructor() : ViewModel() {
    private val _origin = mutableStateOf("")
    val origin get() = _origin.value
    fun setOrigin(origin: String) {
        _origin.value = origin
    }
}
