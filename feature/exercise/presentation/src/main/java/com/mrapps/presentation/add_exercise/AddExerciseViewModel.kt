package com.mrapps.presentation.add_exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.model.exercise_type.ExerciseType
import com.mrapps.domain.use_case.AddNewExerciseUseCase
import com.mrapps.main.util.uuidString
import com.mrapps.presentation.UiText
import com.mrapps.presentation.asUiText
import com.mrapps.presentation.model.ExerciseForm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExerciseViewModel @Inject constructor(
    private val addNewExerciseUseCase: AddNewExerciseUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AddExerciseState())
    val state: StateFlow<AddExerciseState> = _state

    private val _event = Channel<AddExerciseEvent>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    fun onAction(action: AddExerciseAction) {
        when (action) {
            AddExerciseAction.SaveNewExercise -> addExerciseToDatabase(state.value.form)
            is AddExerciseAction.OnNameChange -> updateName(action.name)
            is AddExerciseAction.OnDescriptionChange -> updateDescription(action.description)
            is AddExerciseAction.OnTypeChange -> updateType(action.type)
            is AddExerciseAction.OnTypeFormChange -> updateTypeForm(action.typeForm)
            AddExerciseAction.ClearError -> clearError()
            else -> Unit
        }
    }

    private fun sendEvent(event: AddExerciseEvent) {
        viewModelScope.launch {
            _event.send(event)
        }
    }

    private fun addExerciseToDatabase(form: ExerciseForm) {
        val exercise = Exercise(
            id = uuidString(),
            name = form.name,
            type = form.typeForm,
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

    private fun updateTypeForm(typeForm: ExerciseType) {
        updateForm(state.value.form.copy(typeForm = typeForm))
    }

    private fun updateForm(form: ExerciseForm) {
        _state.value = state.value.copy(form = form)
    }

    private fun clearError() {
        _state.value = state.value.copy(error = null)
    }
}

sealed interface AddExerciseEvent {
    data object OnSuccess : AddExerciseEvent
    data class ShowSnackbar(val message: UiText) : AddExerciseEvent
}