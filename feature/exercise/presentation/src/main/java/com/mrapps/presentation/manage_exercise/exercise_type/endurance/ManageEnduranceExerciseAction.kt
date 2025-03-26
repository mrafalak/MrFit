package com.mrapps.presentation.manage_exercise.exercise_type.endurance

import com.mrapps.domain.model.exercise.type.endurance.EnduranceActivityType
import com.mrapps.domain.model.exercise_type.ExerciseType
import com.mrapps.domain.units.MeasurementUnit

sealed interface ManageEnduranceExerciseAction {
    data class SetInitialTypeForm(val type: ExerciseType.Endurance) : ManageEnduranceExerciseAction
    data class OnActivityTypeChange(val activityType: EnduranceActivityType) :
        ManageEnduranceExerciseAction

    data class OnDurationUnitChange(val durationUnit: MeasurementUnit.Time) :
        ManageEnduranceExerciseAction

    data object ValidateEnduranceForm : ManageEnduranceExerciseAction
    data object ClearError : ManageEnduranceExerciseAction
}