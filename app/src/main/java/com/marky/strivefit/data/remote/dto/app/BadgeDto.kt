package com.marky.strivefit.data.remote.dto.app

import androidx.annotation.Keep

@Keep
data class BadgeDto(
    var id: String = "",
    var badgeName: String = "",
    var description: String = "",
    var designConcept: String = "",
    var keywords: List<String> = emptyList()
)

