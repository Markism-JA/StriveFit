package com.marky.strivefit.data.remote.dto.user

data class WorkoutPlanExerciseDto(
    var id: Int = 0,
    var workoutPlanId: Int,
    var exerciseId: Int,
    var exerciseOrder: Int,
    var sets: Int?,
    var repsMin: Int?,
    var targetRpe: Int?,
    var durationSeconds: Int?,
    var restPeriodSeconds: Int?,
    var notes: String?
)