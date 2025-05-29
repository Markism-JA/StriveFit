package com.marky.strivefit.data.remote.dto.user

import androidx.annotation.Keep

@Keep
data class CyclingSessionDto(
    var id: Int = 0,
    var userId: String,
    var startTime: Long,
    var durationSeconds: Int,
    var distanceMeters: Float,
    var caloriesBUrned: Double,
    var averageSpeedKmh: Float?,
    var maxSpeedKmh: Float?,
    var averageCadenceRpm: Double,
    var maxCadenceRpm: Int?,
    var averageHeartRateBpm: Double?,
    var maxHeartRateBpm: Int?,
    var gpsTrackJson: String?
)