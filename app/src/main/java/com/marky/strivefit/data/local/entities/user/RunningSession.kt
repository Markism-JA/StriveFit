package com.marky.strivefit.data.local.entities.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "running_sessions")
data class RunningSession(
    @PrimaryKey val id: Int = 0,              // Unique session ID (e.g., generated or from Health Connect)
    val userId: String,                      // Reference to user
    val startTime: Date,                   // Epoch millis - session start time
    val durationSeconds: Int,              // Total duration in seconds
    val distanceMeters: Float,             // Total distance covered (meters)
    val caloriesBurned: Double,               // Total calories burned
    val averagePaceSecondsPerKm: Int?,    // Nullable average pace in seconds per kilometer (if available)
    val maxPaceSecondsPerKm: Int?,        // Nullable max pace (fastest segment pace)
    val averageHeartRateBpm: Int?,        // Nullable average heart rate (beats per minute)
    val maxHeartRateBpm: Int?,            // Nullable max heart rate
    val steps: Int?,                       // Nullable step count during run (if available)
    val gpsTrackJson: String?              // Optional serialized GPS track (JSON or encoded polyline)
)