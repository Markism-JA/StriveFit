package com.marky.strivefit.data.remote.dto.user

import androidx.annotation.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp

@Keep
data class RunningSessionDto(
    @DocumentId
    var id: String? = null,

    var userId: String = "",

    var startTime: Timestamp? = null, // When the session started
    var endTime: Timestamp? = null,   // calculated from startTime + duration
    var durationSeconds: Int = 0,

    var distanceMeters: Double = 0.0, // Use Double
    var caloriesBurned: Double = 0.0,

    var averagePaceSecondsPerKm: Int? = null,
    var maxPaceSecondsPerKm: Int? = null,

    var averageHeartRateBpm: Int? = null,
    var maxHeartRateBpm: Int? = null,
    var steps: Int? = null,

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
        averagePaceSecondsPerKm = null,
        maxPaceSecondsPerKm = null,
        averageHeartRateBpm = null,
        maxHeartRateBpm = null,
        steps = null,
        routePath = null,
        notes = null,
        createdAt = null,
    )
}