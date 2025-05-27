package com.marky.strivefit.data.l.values

enum class WorkoutStyle {
    CIRCUIT,     // Multiple exercises performed back-to-back with minimal rest. Great for muscular endurance, cardiovascular fitness, and time efficiency. Can be adapted for hypertrophy or fat loss depending on exercise choice and intensity.
    SUPERSETS,   // Two exercises performed back-to-back, typically for opposing muscle groups (agonist-antagonist) or the same muscle group. Increases intensity, saves time, and can create metabolic stress.
    AMRAP,       // "As Many Rounds/Reps As Possible" within a set time. Pushes work capacity, mental toughness, and conditioning.
    EMOM,        // "Every Minute On The Minute." Perform a set number of reps of an exercise at the start of each minute, resting for the remainder of the minute. Teaches pacing, consistency, and can be used for skill work, strength, or conditioning.
    TRADITIONAL_SETS, // The classic approach: perform a set, rest, perform another set. Ideal for building maximal strength and muscle hypertrophy due to focused effort and controlled rest.
    HIIT,        // "High-Intensity Interval Training." Short bursts of all-out effort followed by brief recovery periods. Excellent for fat loss (EPOC effect), improving anaerobic capacity, and time-efficient cardio.
    LISS         // "Low-Intensity Steady State." Sustained activity at a low to moderate intensity (e.g., brisk walking, light jogging). Good for aerobic base building, recovery, and fat burning with less systemic stress.
}