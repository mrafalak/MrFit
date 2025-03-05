package com.mrapps.domain.model.exercise.type.strength

enum class SetType {

    /**
     * WEIGHT_REPS:
     * Standard sets with a defined weight and a specific number of repetitions.
     * Example: Squats, Bench Press (e.g., 3 sets of 10 reps with 80 kg).
     */
    WEIGHT_REPS,

    /**
     * ISOMETRIC:
     * Exercises where the muscle length does not change during contraction.
     * The set focuses on duration rather than repetitions.
     * Example: Plank, Wall Sit (e.g., hold for 60 seconds).
     */
    ISOMETRIC,

    /**
     * TIMED:
     * Sets based on a specific duration, often used in AMRAP (As Many Reps As Possible) formats.
     * Example: Burpees for 60 seconds, Max Push-ups in 30 seconds.
     */
    TIMED,

    /**
     * REST_PAUSE:
     * Consists of performing as many reps as possible, resting briefly, and repeating.
     * Ideal for increasing intensity in strength training.
     * Example: Drop Sets, Cluster Sets.
     */
    REST_PAUSE,

    /**
     * CIRCUIT:
     * Involves rotating through a series of exercises with minimal rest between them.
     * Great for full-body workouts and improving cardiovascular endurance.
     * Example: 5 exercises, 30 seconds each, 10-second rest between.
     */
    CIRCUIT,

    /**
     * ENDURANCE:
     * Focuses on performing exercises for extended periods or high repetitions.
     * Builds muscular and cardiovascular endurance.
     * Example: Long sets of bodyweight exercises like Jumping Jacks or High Knees.
     */
    ENDURANCE
}