package com.marky.strivefit.data.remote.dto.user

import androidx.annotation.Keep
import java.util.Date

@Keep
data class CompletedChallengeDto(
    var id: Int = 0,
    var challengeId: Int,
    var completionDate: Date,
    var xpEarned: Int
)