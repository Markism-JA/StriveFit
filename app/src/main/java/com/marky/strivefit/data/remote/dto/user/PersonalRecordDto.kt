package com.marky.strivefit.data.remote.dto.user

import androidx.annotation.Keep
import com.marky.strivefit.data.values.PRMetric
import java.util.Date

@Keep
data class PersonalRecordDto(
    var id: Int = 0,
    var userId: String,
    var exerciseId: Int,
    var metric: PRMetric,
    var value: Float,
    var loggedExerciseId: Int?,
    var achievedAt: Date
)