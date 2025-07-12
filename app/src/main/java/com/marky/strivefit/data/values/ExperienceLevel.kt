package com.marky.strivefit.data.values

enum class ExperienceLevel {
    BEGINNER, INTERMEDIATE, ADVANCED;

    companion object {
        fun fromString(value: String): ExperienceLevel? {
            return try {
                valueOf(value.uppercase())
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}
