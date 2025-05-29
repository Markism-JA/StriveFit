package com.marky.strivefit.data.local.entities.app

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Badges",
    indices = [
        Index(value = ["id"], unique = true),
    ]
)
data class BadgeEntity(
    @PrimaryKey val id: Int,
    val badgeName: String,
    val description: String,
    val designConcept: String,
    val keywords: String
)