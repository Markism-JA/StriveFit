package com.marky.strivefit.data.local.entities.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompletedChallenge(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val challengeId: String,
    val completionDate: Long,
    val xpEarned: Int,
)