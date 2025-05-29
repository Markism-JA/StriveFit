 package com.marky.strivefit.data.local.entities.app

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.values.ChallengeType
import com.marky.strivefit.data.values.RepeatInterval

@Entity(
    tableName = "Challenges",
    indices = [
        Index(value = ["name", "createdByUserId"], unique = true),
        Index(value = ["id"], unique = true)
    ],
    foreignKeys = [
        ForeignKey(
           entity = BadgeEntity::class,
            parentColumns = ["id"],
            childColumns = ["badgeId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ]
)
data class ChallengeEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val title: String,
    val description: String,
    val challengeType: ChallengeType, // e.g. steps, workout
    val targetValue: Int,
    val repeatInterval: RepeatInterval,
    val duration: Int,
    val xpReward: Int,
    val badgeId: Int
)