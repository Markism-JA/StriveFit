package com.marky.strivefit.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ChallengeEntity::class,
            parentColumns = ["id"],
            childColumns = ["challengeId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ]
)
data class ActiveChallenge(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val challengeId: String,
    val startDate: Long,
    val currentProgress: Int = 0,
    val streakCount: Int = 0, // if applicable
    val isFailed: Boolean = false
)