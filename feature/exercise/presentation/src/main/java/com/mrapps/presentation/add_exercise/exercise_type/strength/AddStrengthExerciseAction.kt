package com.mrapps.presentation.add_exercise.exercise_type.strength

import com.mrapps.domain.model.exercise.type.strength.ExerciseGoal
import com.mrapps.domain.model.exercise.type.strength.MovementType
import com.mrapps.domain.model.exercise.type.strength.MuscleGroup

sealed interface AddStrengthExerciseAction {
    data object ValidateStrengthForm : AddStrengthExerciseAction
    data class OnMovementTypeChange(val movementType: MovementType) : AddStrengthExerciseAction
    data class OnMuscleGroupChange(val muscleGroup: MuscleGroup) : AddStrengthExerciseAction
    data class OnExerciseGoalChange(val exerciseGoal: ExerciseGoal) : AddStrengthExerciseAction
    data object ClearError : AddStrengthExerciseAction
}