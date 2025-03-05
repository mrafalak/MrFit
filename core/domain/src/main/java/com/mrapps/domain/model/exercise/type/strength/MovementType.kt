package com.mrapps.domain.model.exercise.type.strength

enum class MovementType {

    /**
     * PUSH:
     * Pushing weight away or body from a surface.
     * Examples: Bench Press, Push-ups.
     */
    PUSH,

    /**
     * PULL:
     * Pulling weight or body towards an object.
     * Examples: Pull-ups, Rows.
     */
    PULL,

    /**
     * SQUAT:
     * Bending at hips and knees, targeting lower body.
     * Examples: Back Squat, Bodyweight Squat.
     */
    SQUAT,

    /**
     * HINGE:
     * Bending at hips, focusing on the posterior chain.
     * Examples: Deadlifts, Hip Thrusts.
     */
    HINGE,

    /**
     * CARRY:
     * Carrying weight to improve stability and endurance.
     * Examples: Farmer's Walk, Overhead Carry.
     */
    CARRY,

    /**
     * ROTATION:
     * Twisting the torso, building core strength.
     * Examples: Russian Twists, Wood Choppers.
     */
    ROTATION,

    /**
     * ISOMETRIC:
     * Holding positions for stability and static strength.
     * Examples: Plank, Wall Sit.
     */
    ISOMETRIC
}