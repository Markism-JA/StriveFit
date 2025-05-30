package com.marky.strivefit.data.remote.dto.user

import androidx.annotation.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

@Keep
data class ActiveChallengeDto(
    @DocumentId
    var id: String? = null,
    var userId: String = "",
    var challengeId: String = "",
    var startDate: Timestamp? = null,
    var currentProgress: Int = 0,
    var lastProgressUpdate: Timestamp? = null,
    var streakCount: Int = 0,
    var isFailed: Boolean = false,
    var completedDate: Timestamp? = null,

    @ServerTimestamp
    var createdAt: Timestamp? = null,
    @ServerTimestamp
    var updatedAt: Timestamp? = null
) {
    constructor() : this(
        null,
        "",
        "",
        null,
        0,
        null,
        0,
        false,
        null,
        null,
        null
    )
}

