package com.mrapps.presentation

import androidx.lifecycle.ViewModel
import com.mrapps.domain.use_case.AddNewExerciseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val addNewExerciseUseCase: AddNewExerciseUseCase
) : ViewModel()