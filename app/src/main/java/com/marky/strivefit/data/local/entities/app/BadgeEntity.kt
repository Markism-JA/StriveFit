package com.marky.strivefit.data.local.entities.app

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "Badges",
    indices = [
        Index(value = ["id"], unique = true),
    ]
)
data class BadgeEntity(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: String,

        @ColumnInfo(name = "badge_name")
        val badgeName: String,

        @ColumnInfo(name = "description")
        val description: String,

        @ColumnInfo(name = "design_concept")
        val designConcept: String,

        @ColumnInfo(name = "keywords")
        val keywords: List<String>,

        @ColumnInfo(name = "icon_link")
        var iconLink: String?,

        @ColumnInfo(name = "created_at")
        var createdAt: Date?,

        @ColumnInfo(name = "updated_at")
        var updatedAt: Date?
)