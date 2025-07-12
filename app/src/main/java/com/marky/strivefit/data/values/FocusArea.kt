package com.marky.strivefit.data.values

enum class FocusArea {
    ARMS,
    CHEST,
    BACK,
    SHOULDERS,
    CORE,
    LEGS,
    GLUTES,
    FULL_BODY;

    companion object {
        fun fromString(value: String): FocusArea? {
            return try {
                valueOf(value.uppercase())
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}