package com.mrapps.presentation.manage_exercise.exercise_type.strength

import com.mrapps.domain.model.exercise.type.strength.ExerciseGoal
import com.mrapps.domain.model.exercise.type.strength.MovementType
import com.mrapps.domain.model.exercise.type.strength.MuscleGroup

sealed interface ManageStrengthExerciseAction {
    data object ValidateStrengthForm : ManageStrengthExerciseAction
    data class OnMovementTypeChange(val movementType: MovementType) : ManageStrengthExerciseAction
    data class OnMuscleGroupChange(val muscleGroup: MuscleGroup) : ManageStrengthExerciseAction
    data class OnExerciseGoalChange(val exerciseGoal: ExerciseGoal) : ManageStrengthExerciseAction
    data object ClearError : ManageStrengthExerciseAction
}