package com.mrapps.presentation.add_exercise.endurance

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrapps.domain.model.exercise_type.ExerciseType

@Composable
fun AddEnduranceExerciseScreen(
    onFormChange: (ExerciseType) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddEnduranceExerciseViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val formToUpdate = remember(state) {
        derivedStateOf { state.form }
    }

    LaunchedEffect(formToUpdate.value) {
        onFormChange(formToUpdate.value)
    }
}