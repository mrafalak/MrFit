package com.mrapps.domain.model.exercise

import com.mrapps.domain.model.exercise.type.strength.StrengthSet
import com.mrapps.domain.units.FixedUnitType
import com.mrapps.domain.units.SystemUnitType

sealed class ExerciseType(val type: ExerciseTypeEnum) {

    data class Strength(
        val sets: List<StrengthSet>
    ) : ExerciseType(ExerciseTypeEnum.STRENGTH)

    data class EnduranceTraining(
        val duration: FixedUnitType.Time?,
        val distance: SystemUnitType.Distance?,
        val averageSpeed: SystemUnitType.Speed?
    ) : ExerciseType(ExerciseTypeEnum.ENDURANCE)
}