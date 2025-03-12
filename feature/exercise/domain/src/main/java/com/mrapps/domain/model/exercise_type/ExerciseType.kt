package com.mrapps.domain.model.exercise_type

import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.model.exercise.type.endurance.EnduranceActivityType
import com.mrapps.domain.model.exercise.type.strength.ExerciseGoal
import com.mrapps.domain.model.exercise.type.strength.MovementType
import com.mrapps.domain.model.exercise.type.strength.MuscleGroup
import com.mrapps.domain.units.MeasurementUnit

sealed class ExerciseType(val type: ExerciseTypeEnum) {

    data class Strength(
        val movementType: MovementType? = null,
        val muscleGroup: MuscleGroup? = null,
        val exerciseGoal: ExerciseGoal? = null
    ) : ExerciseType(ExerciseTypeEnum.STRENGTH)

    data class Endurance(
        val activityType: EnduranceActivityType? = null,
        val durationUnit: MeasurementUnit.Time? = null
    ) : ExerciseType(ExerciseTypeEnum.ENDURANCE)
}