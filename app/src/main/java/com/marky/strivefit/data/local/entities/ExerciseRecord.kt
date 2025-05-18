package com.marky.strivefit.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise_record",
    foreignKeys = [
        ForeignKey(
            entity = ExerciseSession::class,
            parentColumns = ["id"],
            childColumns = ["exercise_session_id"]
        ),
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"]
        )
    ]
)
data class ExerciseRecord (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "exercise_session_id") val exerciseSessionId: Int,
    @ColumnInfo(name = "exercise_id") val exerciseId: Int,
    val sets: Int?,
    val reps: Int?,
    val weight: Double?,
    val duration: Long?,
)