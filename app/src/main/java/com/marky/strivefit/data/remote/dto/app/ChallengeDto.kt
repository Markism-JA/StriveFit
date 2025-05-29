package com.marky.strivefit.data.remote.dto.app

import androidx.annotation.Keep
import com.marky.strivefit.data.values.ChallengeType
import com.marky.strivefit.data.values.RepeatInterval

@Keep
data class ChallengeDto(
    var id: Int,
    var name: String,
    var title: String,
    var description: String,
    var challengeType: ChallengeType,
    var targetValue: Int,
    var repeatInterval: RepeatInterval,
    val duration: Int,
    val xpReward: Int,
    val badgeId: Int
)