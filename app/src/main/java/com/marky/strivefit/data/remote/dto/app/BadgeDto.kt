package com.marky.strivefit.data.remote.dto.app

import androidx.annotation.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

@Keep
data class BadgeDto(
    @DocumentId
    var id: String? = null,
    var badgeName: String = "",
    var description: String = "",
    var designConcept: String = "",
    var keywords: List<String> = emptyList(),
    var iconLink: String? = "",

    @ServerTimestamp
    var createdAt: Timestamp? = null,
    var updatedAt: Timestamp? = null
) {
    constructor() : this(
    null,
    "",
        "",
        "",
        emptyList(),
        "",
        null,
        null
    )
}