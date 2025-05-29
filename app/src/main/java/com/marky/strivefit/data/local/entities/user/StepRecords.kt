package com.marky.strivefit.data.local.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.util.Date

@Entity(
    tableName = "step_records",
    primaryKeys = ["userId", "recordDate"],
    foreignKeys = [
        ForeignKey(
            entity = UserDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [Index(value = ["userId"]), Index(value = ["recordDate"])]
)
data class StepRecords(
    @ColumnInfo(name = "userId")
    val userId: String, // UUID

    @ColumnInfo(name = "recordDate")
    val recordDate: Date, // Date only "2025-05-28", each day is overwritten and per day data is collected

    @ColumnInfo(name = "steps")
    val steps: Int = 0,

    @ColumnInfo(name = "distance_km")
    val distanceKm: Float = 0f,

    @ColumnInfo(name = "calories_burned")
    val caloriesBurned: Int = 0,

    @ColumnInfo(name = "source")
    val source: String? = null, // e.g., "GoogleFit", "HealthConnect"

    @ColumnInfo(name = "last_updated")
    var lastUpdated: Date = Date() //date + time "2025-05-28T16:42:00Z"
)