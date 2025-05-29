package com.marky.strivefit.data.remote.dto.user

import androidx.annotation.Keep
import java.util.Date

@Keep
data class LoggedExerciseDto(
    var id: Int = 0,
    var workoutSessionId: Int,
    var workoutPlanExerciseId: Int?,
    var setNumber: Int,
    var repsCompleted: Int?,
    var weightKg: Float?,
    var durationSeconds: Int,
    var distanceKm: Float?,
    var caloriesBurnedPerSet: Int?,
    var actualRpe: Int?,
    var restTakenSeconds: Int?,
    var notes: String?,
    var LoggedAt: Date = Date()
)