package com.mrapps.presentation.exercise_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.use_case.ObserveExerciseListUseCase
import com.mrapps.presentation.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val observeExerciseListUseCase: ObserveExerciseListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ExerciseListState())
    val state: StateFlow<ExerciseListState> = _state
        .onStart {
            observeExercises()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ExerciseListState()
        )

    private val _event = Channel<ExerciseListEvent>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    fun onAction(action: ExerciseListAction) {
        when (action) {
            ExerciseListAction.ClearError -> clearError()
            else -> Unit
        }
    }

    private fun sendEvent(event: ExerciseListEvent) {
        viewModelScope.launch {
            _event.send(event)
        }
    }

    private fun observeExercises() {
        viewModelScope.launch {
            observeExerciseListUseCase.invoke().collectLatest { result ->
                result.fold(
                    onSuccess = { exercises ->
                        updateExerciseList(exercises)
                    },
                    onError = { error ->
                        val errorMessage = error.asUiText()
                        sendEvent(ExerciseListEvent.ShowSnackbar(errorMessage))
                    }
                )
            }
        }
    }

    private fun updateExerciseList(exercises: List<Exercise>) {
        _state.value = state.value.copy(exercises = exercises)
    }

    private fun clearError() {
        _state.value = state.value.copy(error = null)
    }
}