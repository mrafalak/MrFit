package com.mrapps.presentation.add_exercise.exercise_type

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrapps.presentation.model.ExerciseTypeForm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseTypeViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ExerciseTypeState())
    val state: StateFlow<ExerciseTypeState> = _state

    private val _event = MutableSharedFlow<ExerciseTypeEvent>(replay = 1, extraBufferCapacity = 1)
    val event = _event.asSharedFlow()

    fun onAction(action: ExerciseTypeAction) {
        when (action) {
            is ExerciseTypeAction.OnTypeFormChange -> updateTypeForm(
                typeForm = action.typeForm,
                isTypeFormValidated = action.isTypeFormValidated,
            )

            ExerciseTypeAction.ValidateTypeExerciseType -> sendEvent(ExerciseTypeEvent.ValidateTypeForm)
        }
    }

    private fun sendEvent(event: ExerciseTypeEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    private fun updateTypeForm(
        typeForm: ExerciseTypeForm,
        isTypeFormValidated: Boolean,
    ) {
        _state.value = state.value.copy(
            form = typeForm,
            isTypeFormValidated = isTypeFormValidated
        )
    }
}