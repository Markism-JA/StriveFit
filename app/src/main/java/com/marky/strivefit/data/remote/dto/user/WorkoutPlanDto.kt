package com.marky.strivefit.data.remote.dto.user

import com.marky.strivefit.data.values.ExperienceLevel
import java.util.Date

data class WorkoutPlanDto(
    var id: Int = 0,
    var userId: String,
    var name: String,
    var description: String,
    var isPublic: Boolean,
    var difficultyLevel: ExperienceLevel?,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)