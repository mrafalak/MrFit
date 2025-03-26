package com.mrapps.domain.validator

import com.mrapps.domain.Result
import com.mrapps.domain.error.EnduranceExerciseError
import com.mrapps.domain.model.exercise.type.endurance.EnduranceActivityType
import com.mrapps.domain.units.MeasurementUnit
import javax.inject.Inject

class EnduranceExerciseValidator @Inject constructor() {

    fun validateActivityType(activityType: EnduranceActivityType?): Result<Unit, EnduranceExerciseError.ActivityType> {
        return when {
            activityType == null -> Result.Error(EnduranceExerciseError.ActivityType.NOT_SELECTED)
            else -> Result.Success(Unit)
        }
    }

    fun validateDurationUnit(durationUnit: MeasurementUnit.Time?): Result<Unit, EnduranceExerciseError.DurationUnit> {
        return when {
            durationUnit == null -> Result.Error(EnduranceExerciseError.DurationUnit.NOT_SELECTED)
            else -> Result.Success(Unit)
        }
    }
}