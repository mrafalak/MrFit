package com.mrapps.presentation.manage_exercise.exercise_type.endurance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrapps.domain.Result
import com.mrapps.domain.error.EnduranceExerciseError
import com.mrapps.domain.model.exercise.type.endurance.EnduranceActivityType
import com.mrapps.domain.model.exercise_type.ExerciseType
import com.mrapps.domain.units.MeasurementUnit
import com.mrapps.domain.validator.EnduranceExerciseValidator
import com.mrapps.presentation.UiText
import com.mrapps.presentation.model.ExerciseTypeForm
import com.mrapps.presentation.model.ExerciseTypeFormError
import com.mrapps.presentation.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KProperty1

@HiltViewModel
class ManageEnduranceExerciseViewModel @Inject constructor(
    private val formValidator: EnduranceExerciseValidator
) : ViewModel() {

    private val _state = MutableStateFlow(ManageEnduranceExerciseState())
    val state: StateFlow<ManageEnduranceExerciseState> = _state

    fun onAction(action: ManageEnduranceExerciseAction) {
        when (action) {
            is ManageEnduranceExerciseAction.SetInitialTypeForm -> setInitialTypeForm(action.type)
            is ManageEnduranceExerciseAction.OnActivityTypeChange -> updateActivityType(action.activityType)
            is ManageEnduranceExerciseAction.OnDurationUnitChange -> updateDurationUnit(action.durationUnit)
            ManageEnduranceExerciseAction.ValidateEnduranceForm -> validateForm(state.value.form)
            ManageEnduranceExerciseAction.ClearError -> clearError()
        }
    }

    private fun setInitialTypeForm(type: ExerciseType.Endurance) {
        updateFormData(
            state.value.form.data.copy(
                activityType = type.activityType,
                durationUnit = type.durationUnit
            )
        )
    }

    private fun updateActivityType(activityType: EnduranceActivityType) {
        updateFormData(state.value.form.data.copy(activityType = activityType))
    }

    private fun updateDurationUnit(durationUnit: MeasurementUnit.Time) {
        updateFormData(state.value.form.data.copy(durationUnit = durationUnit))
    }

    private fun updateErrors(errors: Map<KProperty1<ExerciseTypeFormError.Endurance, UiText?>, UiText?>) {
        updateFormErrors(
            state.value.form.errors.copy(
                activityType = errors[ExerciseTypeFormError.Endurance::activityType],
                durationUnit = errors[ExerciseTypeFormError.Endurance::durationUnit],
            )
        )
    }

    private fun updateFormData(data: ExerciseType.Endurance) {
        updateForm(state.value.form.copy(data = data))
    }

    private fun updateFormErrors(errors: ExerciseTypeFormError.Endurance) {
        updateForm(state.value.form.copy(errors = errors))
    }

    private fun updateForm(form: ExerciseTypeForm.Endurance) {
        _state.value = state.value.copy(form = form)
    }

    private fun validateForm(typeForm: ExerciseTypeForm.Endurance) {
        viewModelScope.launch {
            val validationResults = listOf(
                async {
                    ExerciseTypeFormError.Endurance::activityType to validateField(
                        typeForm.data.activityType,
                        formValidator::validateActivityType
                    )
                },
                async {
                    ExerciseTypeFormError.Endurance::durationUnit to validateField(
                        typeForm.data.durationUnit,
                        formValidator::validateDurationUnit
                    )
                },
            ).awaitAll().toMap()

            updateErrors(validationResults)

            _state.value = state.value.copy(
                isFormValidated = validationResults.values.all { it == null }
            )
        }
    }

    private suspend fun <T, E : EnduranceExerciseError> validateField(
        value: T,
        validator: suspend (T) -> Result<*, E>
    ): UiText? {
        return (validator(value) as? Result.Error<*, E>)?.error?.asUiText()
    }

    private fun clearError() {
        _state.value = state.value.copy(error = null)
    }
}