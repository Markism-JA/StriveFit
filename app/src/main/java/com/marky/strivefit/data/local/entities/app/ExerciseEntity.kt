package com.marky.strivefit.data.local.entities.app

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.l.values.ExerciseType
import com.marky.strivefit.data.values.BodyParts
import com.marky.strivefit.data.values.Equipments
import com.marky.strivefit.data.values.SecondaryMuscles
import com.marky.strivefit.data.values.Targets

@Entity(
    tableName = "exercises",
    indices = [
        Index(value = ["name", "createdByUserId"], unique = true),
        Index(value = ["id"], unique = true)
    ]
)
data class ExerciseEntity(
    @PrimaryKey val id: Int = 0,
    val name: String,
    val bodyPart: BodyParts,
    val equipment: Equipments,
    val targets: Targets,
    val secondaryMuscles: List<SecondaryMuscles>,
    val instructions: String,
    val exerciseType: ExerciseType
)