package com.mrapps.presentation.add_exercise.exercise_type.strength

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrapps.domain.Result
import com.mrapps.domain.error.StrengthExerciseError
import com.mrapps.domain.model.exercise.type.strength.ExerciseGoal
import com.mrapps.domain.model.exercise.type.strength.MovementType
import com.mrapps.domain.model.exercise.type.strength.MuscleGroup
import com.mrapps.domain.model.exercise_type.ExerciseType
import com.mrapps.domain.validator.StrengthExerciseValidator
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
class AddStrengthExerciseViewModel @Inject constructor(
    val formValidator: StrengthExerciseValidator
) : ViewModel() {

    private val _state = MutableStateFlow(AddStrengthExerciseState())
    val state: StateFlow<AddStrengthExerciseState> = _state

    fun onAction(action: AddStrengthExerciseAction) {
        when (action) {
            is AddStrengthExerciseAction.OnMovementTypeChange -> updateMovementType(action.movementType)
            is AddStrengthExerciseAction.OnMuscleGroupChange -> updateMuscleGroup(action.muscleGroup)
            is AddStrengthExerciseAction.OnExerciseGoalChange -> updateExerciseGoal(action.exerciseGoal)
            AddStrengthExerciseAction.ValidateStrengthForm -> validateForm(state.value.form)
            AddStrengthExerciseAction.ClearError -> clearError()
        }
    }

    private fun updateMovementType(movementType: MovementType) {
        updateFormData(state.value.form.data.copy(movementType = movementType))
    }

    private fun updateMuscleGroup(muscleGroup: MuscleGroup) {
        updateFormData(state.value.form.data.copy(muscleGroup = muscleGroup))
    }

    private fun updateExerciseGoal(exerciseGoal: ExerciseGoal) {
        updateFormData(state.value.form.data.copy(exerciseGoal = exerciseGoal))
    }

    private fun updateErrors(errors: Map<KProperty1<ExerciseTypeFormError.Strength, UiText?>, UiText?>) {
        updateFormErrors(
            state.value.form.errors.copy(
                movementType = errors[ExerciseTypeFormError.Strength::movementType],
                muscleGroup = errors[ExerciseTypeFormError.Strength::muscleGroup],
                exerciseGoal = errors[ExerciseTypeFormError.Strength::exerciseGoal]
            )
        )
    }

    private fun updateFormData(data: ExerciseType.Strength) {
        updateForm(state.value.form.copy(data = data))
    }

    private fun updateFormErrors(errors: ExerciseTypeFormError.Strength) {
        updateForm(state.value.form.copy(errors = errors))
    }

    private fun updateForm(form: ExerciseTypeForm.Strength) {
        _state.value = state.value.copy(form = form)
    }

    private fun validateForm(typeForm: ExerciseTypeForm.Strength) {
        viewModelScope.launch {
            val validationResults = listOf(
                async {
                    ExerciseTypeFormError.Strength::movementType to validateField(
                        typeForm.data.movementType,
                        formValidator::validateMovementType
                    )
                },
                async {
                    ExerciseTypeFormError.Strength::muscleGroup to validateField(
                        typeForm.data.muscleGroup,
                        formValidator::validateMuscleGroup
                    )
                },
                async {
                    ExerciseTypeFormError.Strength::exerciseGoal to validateField(
                        typeForm.data.exerciseGoal,
                        formValidator::validateExerciseGoal
                    )
                }
            ).awaitAll().toMap()

            updateErrors(validationResults)

            _state.value = state.value.copy(
                isFormValidated = validationResults.values.all { it == null }
            )
        }
    }

    private suspend fun <T, E : StrengthExerciseError> validateField(
        value: T,
        validator: suspend (T) -> Result<*, E>
    ): UiText? {
        return (validator(value) as? Result.Error<*, E>)?.error?.asUiText()
    }

    private fun clearError() {
        _state.value = state.value.copy(error = null)
    }
}