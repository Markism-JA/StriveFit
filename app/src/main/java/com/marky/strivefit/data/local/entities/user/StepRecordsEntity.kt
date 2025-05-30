package com.marky.strivefit.data.local.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.util.Date

@Entity(
    tableName = "step_records",
    primaryKeys = ["user_id"],
    foreignKeys = [
        ForeignKey(
            entity = UserDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["user_id"]), Index(value = ["date_string"])]
)
data class StepRecordsEntity(
    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "date_string")
    val dateString: String,

    @ColumnInfo(name = "steps")
    val steps: Int = 0,

    @ColumnInfo(name = "distance_km")
    val distanceKm: Double = 0.0,

    @ColumnInfo(name = "calories_burned")
    val caloriesBurned: Double = 0.0,

    @ColumnInfo(name = "source")
    val source: String? = null,

    @ColumnInfo(name = "last_modified")
    var lastModified: Date?,

    @ColumnInfo(name = "last_synced")
    var lastSynced: Date?
)