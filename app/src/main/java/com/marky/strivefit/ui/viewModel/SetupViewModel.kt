package com.marky.strivefit.ui.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class SetupViewModel @Inject constructor() : ViewModel() {
    private val _setupFinished = MutableStateFlow(Boolean)
    val setupFinished: StateFlow<Boolean.Companion> = _setupFinished.asStateFlow()
}