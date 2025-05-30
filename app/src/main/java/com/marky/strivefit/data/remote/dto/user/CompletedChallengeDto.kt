package com.marky.strivefit.data.remote.dto.user

import androidx.annotation.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

@Keep
data class CompletedChallengeDto(
    @DocumentId
    var id: String? = null,
    var userId: String = "",
    var challengeId: String = "",
    var xpEarned: Int,
    var completionDate: Timestamp? = null,
    var startDate: Timestamp? = null,

    @ServerTimestamp
    var createdAt: Timestamp? = null,
    @ServerTimestamp
    var updatedAt: Timestamp? = null
) {
    constructor() : this(
        null,
        "",
        "",
        completionDate = null,
        xpEarned = 0,
        startDate = null,
        createdAt = null
    )
}