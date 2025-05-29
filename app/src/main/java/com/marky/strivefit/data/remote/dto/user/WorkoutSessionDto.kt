package com.marky.strivefit.data.remote.dto.user

import com.marky.strivefit.data.values.WorkoutSessionStatus
import java.util.Date

data class WorkoutSessionDto(
    var id: Int = 0,
    var userId: String,
    var workoutPlanId: Int?,
    var sessionName: String?,
    var startTime: Date,
    var endTime: Date?,
    var status: WorkoutSessionStatus = WorkoutSessionStatus.STARTED,
    var mood: Int?,
    var perceivedExertion: Int?,
    var totalVolume: Float?,
    var totalCaloriesBurned: Double = 0.0,
    var notes: String,
    var createdAt: Date = Date(),
    var updatedAt: Date = Date()
)