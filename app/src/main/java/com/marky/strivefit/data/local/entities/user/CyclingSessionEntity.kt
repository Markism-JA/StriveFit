package com.marky.strivefit.data.local.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "cycling_sessions",
    foreignKeys = [
        ForeignKey(
            entity = UserDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["user_id"])]
)
data class CyclingSessionEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "start_time")
    val startTime: Date?,

    @ColumnInfo(name = "end_time")
    val endTime: Date?,

    @ColumnInfo(name = "duration_seconds")
    val durationSeconds: Int,

    @ColumnInfo(name = "distance_meters")
    val distanceMeters: Double,

    @ColumnInfo(name = "calories_burned")
    val caloriesBurned: Double,

    @ColumnInfo(name = "average_speed_kmh")
    val averageSpeedKmh: Double?,

    @ColumnInfo(name = "max_speed_kmh")
    val maxSpeedKmh: Double?,

    @ColumnInfo(name = "average_cadence_rpm")
    val averageCadenceRpm: Int?,

    @ColumnInfo(name = "max_cadence_rpm")
    val maxCadenceRpm: Int?,

    @ColumnInfo(name = "average_heart_rate_bpm")
    val averageHeartRateBpm: Int?,

    @ColumnInfo(name = "max_heart_rate_bpm")
    val maxHeartRateBpm: Int?,

    @ColumnInfo(name = "route_path_json")
    val routePathJson: String?,

    @ColumnInfo(name = "notes")
    val notes: String?,

    @ColumnInfo(name = "last_modified")
    var lastModified: Date?,

    @ColumnInfo(name = "last_synced")
    var lastSynced: Date?
)