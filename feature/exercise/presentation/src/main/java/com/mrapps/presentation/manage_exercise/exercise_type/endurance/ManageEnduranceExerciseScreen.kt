package com.mrapps.presentation.manage_exercise.exercise_type.endurance

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrapps.presentation.manage_exercise.exercise_type.ExerciseTypeAction
import com.mrapps.presentation.manage_exercise.exercise_type.ExerciseTypeViewModel

@Composable
fun ManageEnduranceExerciseScreen(
    modifier: Modifier = Modifier,
    sharedViewModel: ExerciseTypeViewModel,
    enduranceViewModel: ManageEnduranceExerciseViewModel = hiltViewModel()
) {
    val enduranceFormState by enduranceViewModel.state.collectAsState()

    LaunchedEffect(enduranceFormState) {
        sharedViewModel.onAction(
            ExerciseTypeAction.OnTypeFormChange(
                typeForm = enduranceFormState.form,
                isTypeFormValidated = enduranceFormState.isFormValidated
            )
        )
    }
}