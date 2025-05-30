package com.marky.strivefit.data.remote.dto.app

import androidx.annotation.Keep
import com.google.firebase.firestore.ServerTimestamp
import com.marky.strivefit.data.values.ChallengeType
import com.marky.strivefit.data.values.RepeatInterval
import java.security.Timestamp

@Keep
data class ChallengeDto(
    var id: String? = null,
    var name: String = "",
    var title: String = " ",
    var description: String = "",
    var challengeType: String = "",
    var targetValue: Int = 0,
    var repeatInterval: String = "",
    val duration: Int = 0,
    val xpReward: Int = 0,
    val badgeId: String? = null,

    @ServerTimestamp
    var createdAt: Timestamp? = null,
    @ServerTimestamp
    var updatedAt: Timestamp? = null
) {
    constructor() : this(
        null,
        "",
        "",
        "",
        "",
        0,
        "",
        0,
        0,
        null,
        null,
        null
    )
}