package com.marky.strivefit.data.local.entities.user

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.marky.strivefit.data.local.entities.app.ChallengeEntity
import java.util.Date

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
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val challengeId: Int,
    val startDate: Date,
    val currentProgress: Int = 0,
    val streakCount: Int = 0, // if applicable
    val isFailed: Boolean = false
)