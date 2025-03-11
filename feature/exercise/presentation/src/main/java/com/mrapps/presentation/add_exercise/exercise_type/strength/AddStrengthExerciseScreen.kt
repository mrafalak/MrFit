package com.mrapps.presentation.add_exercise.exercise_type.strength

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.mrapps.presentation.add_exercise.exercise_type.ExerciseTypeAction
import com.mrapps.presentation.add_exercise.exercise_type.ExerciseTypeEvent
import com.mrapps.presentation.add_exercise.exercise_type.ExerciseTypeViewModel
import com.mrapps.presentation.component.CommonExposedDropdownMenu
import com.mrapps.presentation.preview.ThemePreview
import com.mrapps.presentation.theme.ThemeWithSurface
import com.mrapps.presentation.util.asUiText
import kotlinx.coroutines.flow.collectLatest
import com.mrapps.mrfit.feature.exercise.presentation.R as ExerciseR

@Composable
fun AddStrengthExerciseScreen(
    modifier: Modifier = Modifier,
    sharedViewModel: ExerciseTypeViewModel,
    strengthViewModel: AddStrengthExerciseViewModel = hiltViewModel()
) {
    val strengthFormState by strengthViewModel.state.collectAsState()
    val strengthFormToUpdate = remember(strengthFormState) {
        derivedStateOf { strengthFormState }
    }

    LaunchedEffect(Unit) {
        sharedViewModel.event.collectLatest { event ->
            when (event) {
                ExerciseTypeEvent.ValidateTypeForm -> {
                    strengthViewModel.onAction(AddStrengthExerciseAction.ValidateStrengthForm)
                }
            }
        }
    }

    LaunchedEffect(strengthFormToUpdate.value) {
        sharedViewModel.onAction(
            ExerciseTypeAction.OnTypeFormChange(
                typeForm = strengthFormToUpdate.value.form,
                isTypeFormValidated = strengthFormToUpdate.value.isFormValidated
            )
        )
    }

    AddStrengthExerciseScreenContent(
        modifier,
        strengthFormState,
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
        Text(
            text = stringResource(ExerciseR.string.label_strength_type_data),
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(Modifier.height(8.dp))
        CommonExposedDropdownMenu(
            options = MovementType.entries,
            labelResId = ExerciseR.string.label_movement_type,
            selectedOption = form.data.movementType,
            onOptionSelected = { type ->
                onAction.invoke(AddStrengthExerciseAction.OnMovementTypeChange(type))
            },
            optionToString = {
                it.asUiText().asString(context)
            },
            error = form.errors.movementType
        )
        Spacer(Modifier.height(8.dp))
        CommonExposedDropdownMenu(
            options = MuscleGroup.entries,
            labelResId = ExerciseR.string.label_muscle_goal,
            selectedOption = form.data.muscleGroup,
            onOptionSelected = { type ->
                onAction.invoke(AddStrengthExerciseAction.OnMuscleGroupChange(type))
            },
            optionToString = {
                it.asUiText().asString(context)
            },
            error = form.errors.muscleGroup
        )
        Spacer(Modifier.height(8.dp))
        CommonExposedDropdownMenu(
            options = ExerciseGoal.entries,
            labelResId = ExerciseR.string.label_exercise_goal,
            selectedOption = form.data.exerciseGoal,
            onOptionSelected = { type ->
                onAction.invoke(AddStrengthExerciseAction.OnExerciseGoalChange(type))
            },
            optionToString = {
                it.asUiText().asString(context)
            },
            error = form.errors.exerciseGoal
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