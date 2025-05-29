package com.marky.strivefit.data.local.entities.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "cycling_sessions")
data class CyclingSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val startTime: Date,
    val durationSeconds: Int,
    val distanceMeters: Float,
    val caloriesBurned: Double,
    val averageSpeedKmh: Float?,          // Nullable average speed in km/h
    val maxSpeedKmh: Float?,              // Nullable max speed
    val averageCadenceRpm: Double,          // Nullable average pedal cadence (rpm)
    val maxCadenceRpm: Int?,              // Nullable max cadence
    val averageHeartRateBpm: Double?,        // Nullable average heart rate
    val maxHeartRateBpm: Int?,            // Nullable max heart rate
    val gpsTrackJson: String?              // Optional GPS track data
)