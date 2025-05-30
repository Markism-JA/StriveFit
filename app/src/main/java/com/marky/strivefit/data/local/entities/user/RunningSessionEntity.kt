package com.marky.strivefit.data.local.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "running_sessions",
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
data class RunningSessionEntity(
    @PrimaryKey val id: Int = 0,              // Unique session ID (e.g., generated or from Health Connect)
    @ColumnInfo(name = "user_id")
    val userId: String,                      // Reference to user
    @ColumnInfo(name = "start_time")
    val startTime: Date,                   // Epoch millis - session start time
    @ColumnInfo(name = "duration_seconds")
    val durationSeconds: Int,              // Total duration in seconds
    @ColumnInfo(name = "distance_meters")
    val distanceMeters: Float,             // Total distance covered (meters)
    @ColumnInfo(name = "calories_burned")
    val caloriesBurned: Double,               // Total calories burned
    @ColumnInfo(name = "average_pace_per_seconds_per_km")
    val averagePaceSecondsPerKm: Int?,    // Nullable average pace in seconds per kilometer (if available)
    @ColumnInfo(name = "max_pace_seconds_per_km")
    val maxPaceSecondsPerKm: Int?,        // Nullable max pace (fastest segment pace)
    @ColumnInfo(name = "average_heart_rate_bpm")
    val averageHeartRateBpm: Int?,        // Nullable average heart rate (beats per minute)
    @ColumnInfo(name = "max_heart_rate_bpm")
    val maxHeartRateBpm: Int?,            // Nullable max heart rate
    @ColumnInfo(name = "steps")
    val steps: Int?,                       // Nullable step count during run (if available)
    val gpsTrackJson: String?,              // Optional serialized GPS track (JSON or encoded polyline)

    @ColumnInfo(name = "last_modified")
    var lastModified: Date?,

    @ColumnInfo(name = "last_synced")
    var lastSynced: Date?
)