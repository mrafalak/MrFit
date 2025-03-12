package com.mrapps.presentation.manage_exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrapps.domain.DataError
import com.mrapps.domain.Result
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.use_case.AddNewExerciseUseCase
import com.mrapps.domain.use_case.GetExerciseUseCase
import com.mrapps.domain.use_case.RemoveExerciseUseCase
import com.mrapps.domain.use_case.UpdateExerciseUseCase
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
class ManageExerciseViewModel @Inject constructor(
    private val getExerciseUseCase: GetExerciseUseCase,
    private val addNewExerciseUseCase: AddNewExerciseUseCase,
    private val updateExerciseUseCase: UpdateExerciseUseCase,
    private val removeExerciseUseCase: RemoveExerciseUseCase,
    private val formValidator: ExerciseValidator,
) : ViewModel() {

    private val _state = MutableStateFlow(ManageExerciseState())
    val state: StateFlow<ManageExerciseState> = _state

    private val _event = Channel<ManageExerciseEvent>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    init {
        viewModelScope.launch {
            state.map { it.isFormValidated to it.isTypeFormValidated }
                .distinctUntilChanged()
                .collectLatest { (isFormValidated, isTypeFormValidated) ->
                    if (isFormValidated && isTypeFormValidated) {
                        if (state.value.form.id == null) {
                            addExerciseToDatabase(state.value.form)
                        } else {
                            updateExerciseInDatabase(state.value.form)
                        }
                    }
                }
        }
    }


    fun onAction(action: ManageExerciseAction) {
        when (action) {
            is ManageExerciseAction.GetExerciseData -> getExerciseDataFromDatabase(action.exerciseId)
            ManageExerciseAction.ValidateForm -> {
                validateForm(
                    form = state.value.form,
                    edit = state.value.form.id != null
                )
            }

            is ManageExerciseAction.OnNameChange -> updateName(action.name)
            is ManageExerciseAction.OnDescriptionChange -> updateDescription(action.description)
            is ManageExerciseAction.OnTypeChange -> updateType(action.type)
            is ManageExerciseAction.OnTypeFormChange -> updateTypeForm(
                typeForm = action.typeForm,
                isTypeFormValidated = action.isTypeFormValidated
            )

            ManageExerciseAction.ClearError -> clearError()
            ManageExerciseAction.RemoveExercise -> removeExerciseInDatabase(state.value.form.id)
            else -> Unit
        }
    }

    private fun sendEvent(event: ManageExerciseEvent) {
        viewModelScope.launch {
            _event.send(event)
        }
    }

    private fun getExerciseDataFromDatabase(exerciseId: String) {
        viewModelScope.launch {
            getExerciseUseCase.invoke(exerciseId).fold(
                onSuccess = { exercise ->
                    val updatedForm = state.value.form.copy(
                        id = exercise.id,
                        name = exercise.name,
                        description = exercise.description ?: "",
                    )

                    _state.value = state.value.copy(form = updatedForm)

                    sendEvent(ManageExerciseEvent.SetInitialTypeForm(exercise.type))
                },
                onError = { error ->
                    _state.value = state.value.copy(error = error.asUiText())
                }
            )
        }
    }

    private fun validateForm(form: ExerciseForm, edit: Boolean) {
        viewModelScope.launch {
            val validationResults = listOf(
                async {
                    val validateResult = formValidator.validateName(
                        name = form.name,
                        edit = edit
                    )

                    ExerciseForm::nameError to when (validateResult) {
                        is Result.Error -> {
                            validateResult.error.asUiText()
                        }

                        is Result.Success -> null
                    }
                },
                async {
                    val validateResult =
                        formValidator.validateDescription(description = form.description)

                    ExerciseForm::descriptionError to when (validateResult) {
                        is Result.Error -> {
                            validateResult.error.asUiText()
                        }

                        is Result.Success -> null
                    }
                },
            ).awaitAll().toMap()

            updateErrors(validationResults)

            _state.value = state.value.copy(
                isFormValidated = validationResults.values.all { it == null }
            )
        }
    }

    private fun addExerciseToDatabase(form: ExerciseForm) {
        val exercise = Exercise(
            id = uuidString(),
            name = form.name,
            type = form.typeForm.toExerciseType(),
            description = form.description
        )

        viewModelScope.launch {
            addNewExerciseUseCase.invoke(exercise)
                .fold(
                    onSuccess = {
                        sendEvent(ManageExerciseEvent.OnSuccess)
                    },
                    onError = { error ->
                        val errorMessage = error.asUiText()
                        sendEvent(ManageExerciseEvent.ShowSnackbar(message = errorMessage))
                    }
                )
        }
    }

    private fun updateExerciseInDatabase(form: ExerciseForm) {
        if (form.id == null) {
            _state.value = state.value.copy(error = DataError.Local.UNKNOWN.asUiText())
        } else {
            val exercise = Exercise(
                id = form.id,
                name = form.name,
                description = form.description,
                type = form.typeForm.toExerciseType()
            )

            viewModelScope.launch {
                updateExerciseUseCase.invoke(exercise)
                    .fold(
                        onSuccess = {
                            sendEvent(ManageExerciseEvent.OnSuccess)
                        },
                        onError = { error ->
                            val errorMessage = error.asUiText()
                            sendEvent(ManageExerciseEvent.ShowSnackbar(message = errorMessage))
                        }
                    )
            }
        }
    }

    private fun removeExerciseInDatabase(id: String?) {
        if (id == null) {
            _state.value = state.value.copy(error = DataError.Local.UNKNOWN.asUiText())
        } else {
            viewModelScope.launch {
                removeExerciseUseCase.invoke(id)
                    .fold(
                        onSuccess = {
                            sendEvent(ManageExerciseEvent.OnSuccess)
                        },
                        onError = { error ->
                            val errorMessage = error.asUiText()
                            sendEvent(ManageExerciseEvent.ShowSnackbar(message = errorMessage))
                        }
                    )
            }
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

    private fun clearError() {
        _state.value = state.value.copy(error = null)
    }
}