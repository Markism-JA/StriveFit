package com.marky.strivefit.data.remote.dto.user

import androidx.annotation.Keep
import java.util.Date

@Keep
data class ActiveChallengeDto(
    var id: Int = 0,
    var userId: String,
    var challengeId: Int,
    var startDate: Date,
    var currentProgress: Int = 0,
    var streakCount: Int = 0,
    var isFailed: Boolean = false
)

