package com.mrapps.presentation.model

import com.mrapps.domain.model.exercise_type.ExerciseType

sealed interface ExerciseTypeForm {

    data class Strength(
        val data: ExerciseType.Strength = ExerciseType.Strength(),
        val errors: ExerciseTypeFormError.Strength = ExerciseTypeFormError.Strength()
    ) : ExerciseTypeForm

    data class Endurance(
        val data: ExerciseType.Endurance = ExerciseType.Endurance(),
        val errors: ExerciseTypeFormError.Endurance = ExerciseTypeFormError.Endurance()
    ) : ExerciseTypeForm
}

fun ExerciseTypeForm.toExerciseType(): ExerciseType {
    return when (this) {
        is ExerciseTypeForm.Strength -> this.toExerciseTypeStrength()
        is ExerciseTypeForm.Endurance -> this.toExerciseTypeEndurance()
    }
}

fun ExerciseTypeForm.Strength.toExerciseTypeStrength(): ExerciseType.Strength {
    return ExerciseType.Strength(
        movementType = this.data.movementType,
        muscleGroup = this.data.muscleGroup,
        exerciseGoal = this.data.exerciseGoal
    )
}

fun ExerciseTypeForm.Endurance.toExerciseTypeEndurance(): ExerciseType.Endurance {
    return ExerciseType.Endurance(
        activityType = this.data.activityType,
        durationUnit = this.data.durationUnit,
    )
}