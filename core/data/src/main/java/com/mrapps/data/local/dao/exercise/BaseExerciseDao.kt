package com.mrapps.data.local.dao.exercise

import com.mrapps.data.local.entity.exercise.ExerciseEntity

interface BaseExerciseDao {
    suspend fun insertExercise(exercise: ExerciseEntity)
    suspend fun updateExercise(exercise: ExerciseEntity)
    suspend fun deleteExerciseById(id: String)
}