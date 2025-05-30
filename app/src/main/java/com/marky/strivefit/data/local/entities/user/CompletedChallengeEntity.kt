package com.marky.strivefit.data.local.entities.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class CompletedChallenge(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val challengeId: Int,
    val completionDate: Date,
    val xpEarned: Int,
)