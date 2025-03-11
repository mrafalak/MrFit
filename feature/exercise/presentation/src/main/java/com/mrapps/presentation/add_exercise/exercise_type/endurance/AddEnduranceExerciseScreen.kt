package com.mrapps.presentation.add_exercise.exercise_type.endurance

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrapps.presentation.add_exercise.exercise_type.ExerciseTypeAction
import com.mrapps.presentation.add_exercise.exercise_type.ExerciseTypeViewModel

@Composable
fun AddEnduranceExerciseScreen(
    modifier: Modifier = Modifier,
    sharedViewModel: ExerciseTypeViewModel,
    enduranceViewModel: AddEnduranceExerciseViewModel = hiltViewModel()
) {
    val enduranceFormState by enduranceViewModel.state.collectAsState()
    val enduranceFormToUpdate = remember(enduranceFormState) {
        derivedStateOf { enduranceFormState }
    }

    LaunchedEffect(enduranceFormToUpdate.value) {
        sharedViewModel.onAction(
            ExerciseTypeAction.OnTypeFormChange(
                typeForm = enduranceFormToUpdate.value.form,
                isTypeFormValidated = enduranceFormToUpdate.value.isFormValidated
            )
        )
    }
}