package com.marky.strivefit.data.local.entities.app

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.values.ExerciseType
import com.marky.strivefit.data.values.BodyParts
import com.marky.strivefit.data.values.Equipments
import com.marky.strivefit.data.values.SecondaryMuscles
import com.marky.strivefit.data.values.Targets
import java.util.Date

@Entity(
    tableName = "exercises",
    indices = [
        Index(value = ["name"], unique = true),
        Index(value = ["id"], unique = true)
    ]
) data class ExerciseEntity(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: String,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "body_part")
        val bodyPart: BodyParts,

        @ColumnInfo(name = "equipment")
        val equipment: Equipments,

        @ColumnInfo(name = "target_muscle")
        val targetMuscle: Targets,

        @ColumnInfo(name = "secondary_muscles")
        val secondaryMuscles: List<SecondaryMuscles>,

        @ColumnInfo(name = "instructions")
        val instructions: List<String>,

        @ColumnInfo(name = "exercise_type")
        val exerciseType: ExerciseType,

        @ColumnInfo(name = "gif_url")
        val gifUrl: String = "",

        @ColumnInfo(name = "created_at")
        var createdAt: Date?,

        @ColumnInfo(name = "updated_at")
        var updatedAt: Date?
)