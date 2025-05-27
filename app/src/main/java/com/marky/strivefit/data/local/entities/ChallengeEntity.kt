package com.marky.strivefit.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.values.RepeatInterval

@Entity(
    tableName = "Challenges",
    indices = [
        Index(value = ["name", "createdByUserId"], unique = true),
        Index(value = ["id"], unique = true)
    ]
)
data class ChallengeEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val title: String,
    val description: String,
    val challengeType: String, // e.g. steps, workout
    val targetValue: Int,
    val repeatInterval: RepeatInterval,
    val duration: Int,
    val xpReward: Int,
    val badgeId: Int
)