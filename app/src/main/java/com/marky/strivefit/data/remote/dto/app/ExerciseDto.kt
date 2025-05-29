package com.marky.strivefit.data.remote.dto.app

import androidx.annotation.Keep
import com.marky.strivefit.data.l.values.ExerciseType
import com.marky.strivefit.data.values.BodyParts
import com.marky.strivefit.data.values.Equipments
import com.marky.strivefit.data.values.SecondaryMuscles
import com.marky.strivefit.data.values.Targets

@Keep
data class ExerciseDto(
    var id: String = "",
    var name: String = "",
    var bodyPart: BodyParts = BodyParts.CHEST,
    var equipment: Equipments,
    var targets: Targets,
    var secondaryMuscles: List<SecondaryMuscles>,
    val instructions: String,
    val exerciseType: ExerciseType
)