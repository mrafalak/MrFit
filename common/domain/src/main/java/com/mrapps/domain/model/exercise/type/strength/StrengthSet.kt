package com.mrapps.domain.model.exercise.type.strength

import com.mrapps.domain.units.FixedUnitType
import com.mrapps.domain.units.SystemUnitType

data class StrengthSet(
    val setNumber: Int,
    val reps: FixedUnitType.Repetition?,
    val weight: SystemUnitType.Weight?
)