package com.mrapps.presentation.manage_exercise.exercise_type.endurance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrapps.domain.model.exercise.type.endurance.EnduranceActivityType
import com.mrapps.domain.model.exercise_type.ExerciseType
import com.mrapps.domain.units.MeasurementUnit
import com.mrapps.mrfit.feature.exercise.presentation.R
import com.mrapps.presentation.asUiText
import com.mrapps.presentation.component.CommonExposedDropdownMenu
import com.mrapps.presentation.manage_exercise.exercise_type.ExerciseTypeAction
import com.mrapps.presentation.manage_exercise.exercise_type.ExerciseTypeEvent
import com.mrapps.presentation.manage_exercise.exercise_type.ExerciseTypeViewModel
import com.mrapps.presentation.preview.ThemePreview
import com.mrapps.presentation.theme.ThemeWithSurface
import com.mrapps.presentation.util.asUiText
import kotlinx.coroutines.flow.collectLatest

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

    LaunchedEffect(Unit) {
        sharedViewModel.event.collectLatest { event ->
            when (event) {
                ExerciseTypeEvent.ValidateTypeForm -> {
                    enduranceViewModel.onAction(ManageEnduranceExerciseAction.ValidateEnduranceForm)
                }

                is ExerciseTypeEvent.SetInitialTypeForm -> {
                    if (event.type is ExerciseType.Endurance) {
                        enduranceViewModel.onAction(
                            ManageEnduranceExerciseAction.SetInitialTypeForm(event.type)
                        )
                    }
                }
            }
        }
    }

    ManageEnduranceExerciseScreenContent(
        modifier,
        enduranceFormState,
        enduranceViewModel::onAction
    )
}

@Composable
fun ManageEnduranceExerciseScreenContent(
    modifier: Modifier = Modifier,
    state: ManageEnduranceExerciseState,
    onAction: (ManageEnduranceExerciseAction) -> Unit
) {
    val context = LocalContext.current
    val form = remember(state.form) { state.form }

    Column(modifier) {
        Text(
            text = stringResource(R.string.label_endurance_type_data),
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(Modifier.height(8.dp))
        CommonExposedDropdownMenu(
            options = EnduranceActivityType.entries,
            labelResId = R.string.label_activity_type,
            selectedOption = form.data.activityType,
            onOptionSelected = { type ->
                onAction.invoke(ManageEnduranceExerciseAction.OnActivityTypeChange(type))
            },
            optionToString = {
                it.asUiText().asString(context)
            },
            error = form.errors.activityType
        )
        Spacer(Modifier.height(8.dp))
        CommonExposedDropdownMenu(
            options = MeasurementUnit.Time.entries,
            labelResId = R.string.label_duration_unit,
            selectedOption = form.data.durationUnit,
            onOptionSelected = { unit ->
                onAction.invoke(ManageEnduranceExerciseAction.OnDurationUnitChange(unit))
            },
            optionToString = {
                it.asUiText().asString(context)
            },
            error = form.errors.durationUnit
        )
    }
}

@ThemePreview
@Composable
fun ManageEnduranceExerciseScreenContentPreview(modifier: Modifier = Modifier) {
    ThemeWithSurface {
        ManageEnduranceExerciseScreenContent(
            state = ManageEnduranceExerciseState(),
            onAction = {}
        )
    }
}