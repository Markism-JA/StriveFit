package com.marky.strivefit.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import com.marky.strivefit.data.values.Equipments

@Entity(
    tableName = "user_available_equipment",
    primaryKeys = ["userId", "equipmentMasterId"],
    foreignKeys = [
        ForeignKey(
            entity = UserDataEntity::class,
            parentColumns = ["uuid"],
            childColumns = ["userId"],
            onDelete = ForeignKey.Companion.CASCADE
        )

    ]
)
data class UserAvailableEquipmentEntity(
    val userId: String,
    val equipment: Equipments
)