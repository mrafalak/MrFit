package com.mrapps.presentation.manage_exercise.exercise_type

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.presentation.manage_exercise.exercise_type.endurance.ManageEnduranceExerciseScreen
import com.mrapps.presentation.manage_exercise.exercise_type.strength.ManageStrengthExerciseScreen

@Composable
fun ExerciseTypeScreen(
    modifier: Modifier = Modifier,
    type: ExerciseTypeEnum,
    sharedViewModel: ExerciseTypeViewModel
) {
    when (type) {
        ExerciseTypeEnum.STRENGTH -> {
            ManageStrengthExerciseScreen(
                modifier = modifier,
                sharedViewModel = sharedViewModel
            )
        }

        ExerciseTypeEnum.ENDURANCE -> {
            ManageEnduranceExerciseScreen(
                modifier = modifier,
                sharedViewModel = sharedViewModel
            )
        }
    }
}