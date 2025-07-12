package com.marky.strivefit.data.values
enum class Sex {
    MALE,
    FEMALE;

    companion object {
        fun fromString(value: String): Sex? {
            return try {
                valueOf(value.uppercase())
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}