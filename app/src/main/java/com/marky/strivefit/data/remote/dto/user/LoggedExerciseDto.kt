package com.marky.strivefit.data.remote.dto.user

import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.security.Timestamp
import java.util.Date
@Keep
data class LoggedExerciseDto(
    @DocumentId
    var id: String? = null, // Firestore document ID for this logged exercise instance

    var workoutSessionId: String? = null, // Reference to the parent WorkoutSessionDto.id (String)
    var exerciseId: String? = null, // Reference to the main ExerciseDto.id (String) from 'app' DTOs
    // this links to the definition of the exercise performed.
    var workoutPlanExerciseId: String? = null,

    var setNumber: Int = 1,

    // Performance metrics
    var repsCompleted: Int? = null,
    var weightKg: Double? = null,
    var durationSeconds: Int? = null,
    var distanceKm: Double? = null,
    var caloriesBurnedPerSet: Int? = null,
    var actualRpe: Int? = null,
    var restTakenSeconds: Int? = null,

    var notes: String? = null,

    // Timestamps
    var loggedAt: Timestamp? = null,

    @ServerTimestamp
    var createdAt: Timestamp? = null,
    @ServerTimestamp
    var updatedAt: Timestamp? = null
) {
    constructor() : this(
        id = null,
        workoutSessionId = null,
        exerciseId = null,
        workoutPlanExerciseId = null,
        setNumber = 1,
        repsCompleted = null,
        weightKg = null,
        durationSeconds = null,
        distanceKm = null,
        caloriesBurnedPerSet = null,
        actualRpe = null,
        restTakenSeconds = null,
        notes = null,
        loggedAt = null,
        createdAt = null,
        updatedAt = null
    )
}