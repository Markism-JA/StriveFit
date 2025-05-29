package com.marky.strivefit.data.remote.dto.user

import com.marky.strivefit.data.values.Equipments

data class UserAvailableEquipmentDto(
    var userId: String,
    var equipment: Equipments
)