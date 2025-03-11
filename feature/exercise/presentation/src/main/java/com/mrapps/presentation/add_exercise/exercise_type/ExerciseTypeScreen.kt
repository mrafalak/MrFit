package com.mrapps.presentation.add_exercise.exercise_type

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.presentation.add_exercise.exercise_type.endurance.AddEnduranceExerciseScreen
import com.mrapps.presentation.add_exercise.exercise_type.strength.AddStrengthExerciseScreen

@Composable
fun ExerciseTypeScreen(
    modifier: Modifier = Modifier,
    type: ExerciseTypeEnum,
    sharedViewModel: ExerciseTypeViewModel
) {
    when (type) {
        ExerciseTypeEnum.STRENGTH -> {
            AddStrengthExerciseScreen(
                modifier = modifier,
                sharedViewModel = sharedViewModel
            )
        }

        ExerciseTypeEnum.ENDURANCE -> {
            AddEnduranceExerciseScreen(
                modifier = modifier,
                sharedViewModel = sharedViewModel
            )
        }
    }
}