package com.mrapps.presentation.manage_exercise.exercise_type.endurance

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ManageEnduranceExerciseViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ManageEnduranceExerciseState())
    val state: StateFlow<ManageEnduranceExerciseState> = _state
}