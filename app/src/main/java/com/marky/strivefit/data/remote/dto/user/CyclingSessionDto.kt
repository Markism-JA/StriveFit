package com.marky.strivefit.data.remote.dto.user

import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp
import java.security.Timestamp

@Keep
data class CyclingSessionDto(
    @DocumentId
    var id: String? = null,

    var userId: String = "",

    var startTime: Timestamp? = null,
    var endTime: Timestamp? = null,
    var durationSeconds: Int = 0,

    var distanceMeters: Double = 0.0,
    var caloriesBurned: Double = 0.0,

    var averageSpeedKmh: Double? = null,
    var maxSpeedKmh: Double? = null,

    var averageCadenceRpm: Int? = null,
    var maxCadenceRpm: Int? = null,

    var averageHeartRateBpm: Int? = null,
    var maxHeartRateBpm: Int? = null,


    var routePath: List<GeoPoint>? = null,

    var notes: String? = null,

    @ServerTimestamp
    var createdAt: Timestamp? = null,
    @ServerTimestamp
    var updatedAt: Timestamp? = null
) {
    constructor() : this(
        id = null,
        userId = "",
        startTime = null,
        endTime = null,
        durationSeconds = 0,
        distanceMeters = 0.0,
        caloriesBurned = 0.0,
        averageSpeedKmh = null,
        maxSpeedKmh = null,
        averageCadenceRpm = null,
        maxCadenceRpm = null,
        averageHeartRateBpm = null,
        maxHeartRateBpm = null,
        routePath = null,
        notes = null,
        createdAt = null,
        updatedAt = null
    )
}