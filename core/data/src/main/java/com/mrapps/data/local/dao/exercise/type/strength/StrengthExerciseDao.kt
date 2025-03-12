package com.mrapps.data.local.dao.exercise.type.strength

import androidx.room.Dao
import androidx.room.Query
import com.mrapps.data.local.entity.exercise.type.StrengthExerciseEntity

@Dao
interface StrengthExerciseDao {

    @Query("SELECT * FROM strength_exercise_entity")
    suspend fun getAllStrengthExercises(): List<StrengthExerciseEntity>

    @Query("SELECT * FROM strength_exercise_entity WHERE exercise_id = :id")
    suspend fun getStrengthExerciseById(id: String): StrengthExerciseEntity
}