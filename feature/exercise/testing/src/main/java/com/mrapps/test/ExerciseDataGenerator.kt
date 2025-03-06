package com.mrapps.test

import com.mrapps.domain.model.Exercise
import com.mrapps.domain.model.exercise.type.strength.ExerciseGoal
import com.mrapps.domain.model.exercise.type.strength.MovementType
import com.mrapps.domain.model.exercise.type.strength.MuscleGroup
import com.mrapps.domain.model.exercise_type.ExerciseType
import com.mrapps.main.util.uuidString

fun exercise() = Exercise(
    id = uuidString(),
    name = "Bench Press",
    type = ExerciseType.Strength(
        movementType = MovementType.PUSH,
        muscleGroup = MuscleGroup.CHEST,
        exerciseGoal = ExerciseGoal.STRENGTH
    ),
    description = null,
)