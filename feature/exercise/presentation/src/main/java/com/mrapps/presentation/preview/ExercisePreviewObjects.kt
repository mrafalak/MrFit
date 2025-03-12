package com.mrapps.presentation.preview

import com.mrapps.domain.model.Exercise
import com.mrapps.domain.model.exercise.type.strength.ExerciseGoal
import com.mrapps.domain.model.exercise.type.strength.MovementType
import com.mrapps.domain.model.exercise.type.strength.MuscleGroup
import com.mrapps.domain.model.exercise_type.ExerciseType

object ExercisePreviewObjects {

    val exercise = Exercise(
        id = "1",
        name = "Push-ups",
        type = ExerciseType.Strength(
            movementType = MovementType.PUSH,
            muscleGroup = MuscleGroup.CHEST,
            exerciseGoal = ExerciseGoal.STRENGTH
        ),
        description = null
    )
}