package com.marky.strivefit.data.remote.dto.user

import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.security.Timestamp

@Keep
data class StepRecordDto(
    @DocumentId
    var dateString: String = "",

    var userId: String = "",

    var steps: Int = 0,
    var distanceKm: Double = 0.0,
    var caloriesBurned: Double = 0.0,
    var source: String? = null, // e.g., "Google Fit", "Apple Health", "Manual Entry"

    @ServerTimestamp
    var createdAt: Timestamp? = null,
    @ServerTimestamp
    var lastUpdatedAt: Timestamp? = null
) {
    constructor() : this(
        dateString = "",
        userId = "",
        steps = 0,
        distanceKm = 0.0,
        caloriesBurned = 0.0,
        source = null,
        createdAt = null,
        lastUpdatedAt = null
    )
}