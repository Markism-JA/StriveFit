package com.marky.strivefit.data.remote.dto.app

import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.security.Timestamp

@Keep
data class ExerciseDto(
    @DocumentId
    var id: String? = null,
    var name: String = "",
    var bodyPart: String = "",
    var equipment: String = "",
    var targets: String = "",
    var secondaryMuscles: List<String> = emptyList(),
    val instructions: List<String> = emptyList(),
    val exerciseType: String = "",
    var gifUrl: String = "",

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
        emptyList(),
        emptyList(),
        "",
        "")
}

