package com.marky.strivefit.data.remote.dto.user

import androidx.annotation.Keep
import java.util.Date

@Keep
data class StepRecordsDto(
    var userId: String,
    var recordDate: Date,
    var steps: Int = 0,
    var distanceKm: Float = 0f,
    var caloriesBurned: Double = 0.0,
    var source: String? = null,
    var lastUpdated: Date = Date()
)