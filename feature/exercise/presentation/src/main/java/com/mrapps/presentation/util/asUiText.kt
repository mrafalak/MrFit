package com.mrapps.presentation.util

import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.model.exercise.type.strength.ExerciseGoal
import com.mrapps.domain.model.exercise.type.strength.MovementType
import com.mrapps.domain.model.exercise.type.strength.MuscleGroup
import com.mrapps.mrfit.feature.exercise.presentation.R
import com.mrapps.presentation.UiText

fun ExerciseTypeEnum.asUiText(): UiText {
    return when (this) {
        ExerciseTypeEnum.STRENGTH -> UiText.StringResource(R.string.exercise_type_strength)
        ExerciseTypeEnum.ENDURANCE -> UiText.StringResource(R.string.exercise_type_endurance)
    }
}

fun MovementType.asUiText(): UiText {
    return when (this) {
        MovementType.PUSH -> UiText.StringResource(R.string.movement_type_push)
        MovementType.PULL -> UiText.StringResource(R.string.movement_type_pull)
        MovementType.SQUAT -> UiText.StringResource(R.string.movement_type_squat)
        MovementType.HINGE -> UiText.StringResource(R.string.movement_type_hinge)
        MovementType.CARRY -> UiText.StringResource(R.string.movement_type_carry)
        MovementType.ROTATION -> UiText.StringResource(R.string.movement_type_rotation)
        MovementType.ISOMETRIC -> UiText.StringResource(R.string.movement_type_isometric)
    }
}

fun MuscleGroup.asUiText(): UiText {
    return when (this) {
        MuscleGroup.UPPER_BODY -> UiText.StringResource(R.string.muscle_group_upper_body)
        MuscleGroup.LOWER_BODY -> UiText.StringResource(R.string.muscle_group_lower_body)
        MuscleGroup.FULL_BODY -> UiText.StringResource(R.string.muscle_group_full_body)
        MuscleGroup.CORE -> UiText.StringResource(R.string.muscle_group_core)
        MuscleGroup.BACK -> UiText.StringResource(R.string.muscle_group_back)
        MuscleGroup.LEGS -> UiText.StringResource(R.string.muscle_group_legs)
        MuscleGroup.ARMS -> UiText.StringResource(R.string.muscle_group_arms)
        MuscleGroup.CHEST -> UiText.StringResource(R.string.muscle_group_chest)
        MuscleGroup.SHOULDERS -> UiText.StringResource(R.string.muscle_group_shoulders)
    }
}

fun ExerciseGoal.asUiText(): UiText {
    return when (this) {
        ExerciseGoal.STRENGTH -> UiText.StringResource(R.string.exercise_goal_strength)
        ExerciseGoal.HYPERTROPHY -> UiText.StringResource(R.string.exercise_goal_hypertrophy)
        ExerciseGoal.ENDURANCE -> UiText.StringResource(R.string.exercise_goal_endurance)
        ExerciseGoal.MOBILITY -> UiText.StringResource(R.string.exercise_goal_mobility)
        ExerciseGoal.STABILITY -> UiText.StringResource(R.string.exercise_goal_stability)
        ExerciseGoal.REHABILITATION -> UiText.StringResource(R.string.exercise_goal_rehabilitation)
    }
}