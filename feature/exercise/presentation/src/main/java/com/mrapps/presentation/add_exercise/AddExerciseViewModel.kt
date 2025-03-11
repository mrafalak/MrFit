package com.mrapps.presentation.add_exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrapps.domain.Result
import com.mrapps.domain.error.ExerciseError
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.use_case.AddNewExerciseUseCase
import com.mrapps.domain.validator.ExerciseValidator
import com.mrapps.main.util.uuidString
import com.mrapps.presentation.UiText
import com.mrapps.presentation.asUiText
import com.mrapps.presentation.model.ExerciseForm
import com.mrapps.presentation.model.ExerciseTypeForm
import com.mrapps.presentation.model.toExerciseType
import com.mrapps.presentation.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KProperty1

@HiltViewModel
class AddExerciseViewModel @Inject constructor(
    private val addNewExerciseUseCase: AddNewExerciseUseCase,
    private val formValidator: ExerciseValidator,
) : ViewModel() {

    private val _state = MutableStateFlow(AddExerciseState())
    val state: StateFlow<AddExerciseState> = _state

    private val _event = Channel<AddExerciseEvent>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    init {
        viewModelScope.launch {
            state.map { it.isFormValidated to it.isTypeFormValidated }
                .distinctUntilChanged()
                .collectLatest { (isFormValidated, isTypeFormValidated) ->
                    if (isFormValidated && isTypeFormValidated) {
                        addExerciseToDatabase(state.value.form)
                    }
                }
        }
    }


    fun onAction(action: AddExerciseAction) {
        when (action) {
            AddExerciseAction.ValidateForm -> validateForm(state.value.form)
            is AddExerciseAction.OnNameChange -> updateName(action.name)
            is AddExerciseAction.OnDescriptionChange -> updateDescription(action.description)
            is AddExerciseAction.OnTypeChange -> updateType(action.type)
            is AddExerciseAction.OnTypeFormChange -> updateTypeForm(
                typeForm = action.typeForm,
                isTypeFormValidated = action.isTypeFormValidated
            )

            AddExerciseAction.ClearError -> clearError()
            else -> Unit
        }
    }

    private fun sendEvent(event: AddExerciseEvent) {
        viewModelScope.launch {
            _event.send(event)
        }
    }

    private fun validateForm(form: ExerciseForm) {
        viewModelScope.launch {
            val validationResults = listOf(
                async {
                    ExerciseForm::nameError to validateField(
                        value = form.name,
                        validator = formValidator::validateName,
                    )
                },
                async {
                    ExerciseForm::descriptionError to validateField(
                        value = form.description,
                        validator = formValidator::validateDescription,
                    )
                },
            ).awaitAll().toMap()

            updateErrors(validationResults)

            _state.value = state.value.copy(
                isFormValidated = validationResults.values.all { it == null }
            )
        }
    }

    private fun addExerciseToDatabase(form: ExerciseForm) {
        val exerciseType = form.typeForm.toExerciseType()
        val exercise = Exercise(
            id = uuidString(),
            name = form.name,
            type = exerciseType,
            description = form.description
        )

        viewModelScope.launch {
            addNewExerciseUseCase.invoke(exercise)
                .fold(
                    onSuccess = {
                        sendEvent(AddExerciseEvent.OnSuccess)
                    },
                    onError = { error ->
                        val errorMessage = error.asUiText()
                        sendEvent(AddExerciseEvent.ShowSnackbar(message = errorMessage))
                    }
                )
        }
    }

    private fun updateName(name: String) {
        updateForm(state.value.form.copy(name = name))
    }

    private fun updateDescription(description: String) {
        updateForm(state.value.form.copy(description = description))
    }

    private fun updateType(type: ExerciseTypeEnum) {
        updateForm(state.value.form.copy(type = type))
    }

    private fun updateTypeForm(
        typeForm: ExerciseTypeForm,
        isTypeFormValidated: Boolean
    ) {
        _state.value = state.value.copy(
            form = state.value.form.copy(
                typeForm = typeForm
            ),
            isTypeFormValidated = isTypeFormValidated
        )
    }

    private fun updateErrors(errors: Map<KProperty1<ExerciseForm, UiText?>, UiText?>) {
        updateForm(
            state.value.form.copy(
                nameError = errors[ExerciseForm::nameError],
                descriptionError = errors[ExerciseForm::descriptionError],
            )
        )
    }

    private fun updateForm(form: ExerciseForm) {
        _state.value = state.value.copy(form = form)
    }

    private suspend fun <T, E : ExerciseError> validateField(
        value: T,
        validator: suspend (T) -> Result<*, E>
    ): UiText? {
        return (validator(value) as? Result.Error<*, E>)?.error?.asUiText()
    }

    private fun clearError() {
        _state.value = state.value.copy(error = null)
    }
}