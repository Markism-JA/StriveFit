package com.marky.strivefit.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Progress @Inject constructor() : ViewModel() {
    private val  _progress = MutableLiveData<Float>()
    val progress: LiveData<Float> get() = _progress
    fun updateProgress(step: Int, totalSteps: Int) {
        _progress.value = step.toFloat() / totalSteps
    }
}