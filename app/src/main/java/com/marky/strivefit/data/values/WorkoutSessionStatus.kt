package com.marky.strivefit.data.values

enum class WorkoutSessionStatus {
    STARTED,     // Session has been started but not yet finished
    COMPLETED,   // Session was finished normally
    CANCELLED,   // User explicitly stopped or deleted the session
    ABANDONED,   // System marks this if user left it hanging too long (e.g., never resumed)
    PAUSED       // (Optional) If you support temporarily pausing a session
}