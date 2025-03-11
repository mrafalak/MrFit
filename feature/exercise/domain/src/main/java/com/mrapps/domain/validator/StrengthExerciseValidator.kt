package com.mrapps.domain.validator

import com.mrapps.domain.Result
import com.mrapps.domain.error.StrengthExerciseError
import com.mrapps.domain.model.exercise.type.strength.ExerciseGoal
import com.mrapps.domain.model.exercise.type.strength.MovementType
import com.mrapps.domain.model.exercise.type.strength.MuscleGroup
import javax.inject.Inject

class StrengthExerciseValidator @Inject constructor() {

    fun validateMovementType(movementType: MovementType?): Result<Unit, StrengthExerciseError.MovementType> {
        return when {
            movementType == null -> Result.Error(StrengthExerciseError.MovementType.NOT_SELECTED)
            else -> Result.Success(Unit)
        }
    }

    fun validateMuscleGroup(muscleGroup: MuscleGroup?): Result<Unit, StrengthExerciseError.MuscleGroup> {
        return when {
            muscleGroup == null -> Result.Error(StrengthExerciseError.MuscleGroup.NOT_SELECTED)
            else -> Result.Success(Unit)
        }
    }

    fun validateExerciseGoal(exerciseGoal: ExerciseGoal?): Result<Unit, StrengthExerciseError.ExerciseGoal> {
        return when {
            exerciseGoal == null -> Result.Error(StrengthExerciseError.ExerciseGoal.NOT_SELECTED)
            else -> Result.Success(Unit)
        }
    }
}