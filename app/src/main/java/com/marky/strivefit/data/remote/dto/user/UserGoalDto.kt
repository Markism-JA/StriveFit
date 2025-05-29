package com.marky.strivefit.data.remote.dto.user

import com.marky.strivefit.data.values.GoalType
import com.marky.strivefit.data.values.RepeatInterval
import java.util.Date

data class UserGoalDto(
    var id: Int = 0,
    var userId: Int?,
    var exerciseId: Int?,
    var goalType: GoalType,
    var targetValue: Int,
    var currentValue: Int = 0,
    var startDate: Date,
    var endDate: Date,
    var isCompleted: Boolean = false,

    var targetFrequency: Int = 1,
    var repeatInterval: RepeatInterval = RepeatInterval.NONE,
    var repeatDays: String? = null
)