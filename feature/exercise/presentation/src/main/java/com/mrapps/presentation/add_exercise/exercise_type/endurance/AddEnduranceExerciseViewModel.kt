package com.mrapps.presentation.add_exercise.exercise_type.endurance

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddEnduranceExerciseViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(AddEnduranceExerciseState())
    val state: StateFlow<AddEnduranceExerciseState> = _state
}