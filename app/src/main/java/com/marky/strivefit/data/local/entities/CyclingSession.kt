package com.marky.strivefit.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cycling_sessions")
data class CyclingSession(
    @PrimaryKey val id: Long,
    val userId: Long,
    val startTime: Long,
    val durationSeconds: Int,
    val distanceMeters: Float,
    val caloriesBurned: Int,
    val averageSpeedKmh: Float?,          // Nullable average speed in km/h
    val maxSpeedKmh: Float?,              // Nullable max speed
    val averageCadenceRpm: Int?,          // Nullable average pedal cadence (rpm)
    val maxCadenceRpm: Int?,              // Nullable max cadence
    val averageHeartRateBpm: Int?,        // Nullable average heart rate
    val maxHeartRateBpm: Int?,            // Nullable max heart rate
    val gpsTrackJson: String?              // Optional GPS track data
)