package com.marky.strivefit.data.remote.dto.user

import java.time.LocalTime

data class WorkoutPreferences(
    var userId: String,
    var daysPerWeek: Int,
    var sessionDurationMinutes: Int,
    var workoutReminder: LocalTime
)