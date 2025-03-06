package com.mrapps.domain.model.exercise_type

import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.model.exercise.type.strength.ExerciseGoal
import com.mrapps.domain.model.exercise.type.strength.MovementType
import com.mrapps.domain.model.exercise.type.strength.MuscleGroup
import com.mrapps.domain.units.FixedUnitType
import com.mrapps.domain.units.SystemUnitType

sealed class ExerciseType(val type: ExerciseTypeEnum) {

    data class Strength(
        val movementType: MovementType,
        val muscleGroup: MuscleGroup,
        val exerciseGoal: ExerciseGoal?
    ) : ExerciseType(ExerciseTypeEnum.STRENGTH)

    data class Endurance(
        val duration: FixedUnitType.Time?,
        val distance: SystemUnitType.Distance?,
        val averageSpeed: SystemUnitType.Speed?
    ) : ExerciseType(ExerciseTypeEnum.ENDURANCE)
}