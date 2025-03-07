package com.mrapps.presentation.add_exercise.strength

import androidx.lifecycle.ViewModel
import com.mrapps.domain.model.exercise.type.strength.ExerciseGoal
import com.mrapps.domain.model.exercise.type.strength.MovementType
import com.mrapps.domain.model.exercise.type.strength.MuscleGroup
import com.mrapps.domain.model.exercise_type.ExerciseType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddStrengthExerciseViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(AddStrengthExerciseState())
    val state: StateFlow<AddStrengthExerciseState> = _state

    fun onAction(action: AddStrengthExerciseAction) {
        when (action) {
            is AddStrengthExerciseAction.OnMovementTypeChange -> updateMovementType(action.movementType)
            is AddStrengthExerciseAction.OnMuscleGroupChange -> updateMuscleGroup(action.muscleGroup)
            is AddStrengthExerciseAction.OnExerciseGoalChange -> updateExerciseGoal(action.exerciseGoal)
            AddStrengthExerciseAction.ClearError -> clearError()
        }
    }

    private fun updateMovementType(movementType: MovementType) {
        updateForm(state.value.form.copy(movementType = movementType))
    }

    private fun updateMuscleGroup(muscleGroup: MuscleGroup) {
        updateForm(state.value.form.copy(muscleGroup = muscleGroup))
    }

    private fun updateExerciseGoal(exerciseGoal: ExerciseGoal) {
        updateForm(state.value.form.copy(exerciseGoal = exerciseGoal))
    }

    private fun updateForm(form: ExerciseType.Strength) {
        _state.value = state.value.copy(form = form)
    }

    private fun clearError() {
        _state.value = state.value.copy(error = null)
    }
}