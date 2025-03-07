package com.mrapps.presentation.add_exercise.strength

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrapps.domain.model.exercise.type.strength.ExerciseGoal
import com.mrapps.domain.model.exercise.type.strength.MovementType
import com.mrapps.domain.model.exercise.type.strength.MuscleGroup
import com.mrapps.domain.model.exercise_type.ExerciseType
import com.mrapps.presentation.component.ExposedDropdownMenuSample
import com.mrapps.presentation.preview.ThemePreview
import com.mrapps.presentation.theme.ThemeWithSurface
import com.mrapps.presentation.util.asUiText
import com.mrapps.mrfit.feature.exercise.presentation.R as ExerciseR

@Composable
fun AddStrengthExerciseScreen(
    onFormChange: (ExerciseType) -> Unit,
    modifier: Modifier = Modifier,
    strengthViewModel: AddStrengthExerciseViewModel = hiltViewModel()
) {
    val state by strengthViewModel.state.collectAsState()
    val formToUpdate = remember(state) {
        derivedStateOf { state.form }
    }

    LaunchedEffect(formToUpdate.value) {
        onFormChange(formToUpdate.value)
    }

    AddStrengthExerciseScreenContent(
        modifier,
        state,
        strengthViewModel::onAction
    )
}

@Composable
fun AddStrengthExerciseScreenContent(
    modifier: Modifier = Modifier,
    state: AddStrengthExerciseState,
    onAction: (AddStrengthExerciseAction) -> Unit
) {
    val context = LocalContext.current
    val form = remember(state.form) { state.form }

    Column(modifier) {
        ExposedDropdownMenuSample(
            options = MovementType.entries,
            label = stringResource(ExerciseR.string.label_movement_type),
            selectedOption = form.movementType,
            onOptionSelected = { type ->
                onAction.invoke(AddStrengthExerciseAction.OnMovementTypeChange(type))
            },
            optionToString = {
                it.asUiText().asString(context)
            }
        )
        Spacer(Modifier.height(8.dp))
        ExposedDropdownMenuSample(
            options = MuscleGroup.entries,
            label = stringResource(ExerciseR.string.label_muscle_goal),
            selectedOption = form.muscleGroup,
            onOptionSelected = { type ->
                onAction.invoke(AddStrengthExerciseAction.OnMuscleGroupChange(type))
            },
            optionToString = {
                it.asUiText().asString(context)
            }
        )
        Spacer(Modifier.height(8.dp))
        ExposedDropdownMenuSample(
            options = ExerciseGoal.entries,
            label = stringResource(ExerciseR.string.label_exercise_goal),
            selectedOption = form.exerciseGoal,
            onOptionSelected = { type ->
                onAction.invoke(AddStrengthExerciseAction.OnExerciseGoalChange(type))
            },
            optionToString = {
                it.asUiText().asString(context)
            }
        )
    }
}

@ThemePreview
@Composable
fun AddStrengthExercisePreview(modifier: Modifier = Modifier) {
    ThemeWithSurface {
        AddStrengthExerciseScreenContent(
            state = AddStrengthExerciseState(),
            onAction = {}
        )
    }
}