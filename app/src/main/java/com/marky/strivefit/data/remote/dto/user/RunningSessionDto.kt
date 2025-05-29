package com.marky.strivefit.data.remote.dto.user

import androidx.annotation.Keep
import java.util.Date

@Keep
data class RunningSessionDto(
    var id: Int = 0,
    var userId: String,
    var startTime: Date = Date(),
    var durationSeconds: Int,
    var distanceMeters: Float,
    var caloriesBurned: Double,
    var averagePaceSecondsPerKm: Int?,
    var maxPaceSecondsPerKm: Int?,
    var averageHeartRateBpm: Int?,
    var maxHeartRateBpm: Int?,
    var steps: Int?,
    var gpsTrackJson: String?
)