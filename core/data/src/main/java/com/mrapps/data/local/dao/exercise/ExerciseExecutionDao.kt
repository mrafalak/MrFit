package com.mrapps.data.local.dao.exercise

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mrapps.data.local.entity.exercise.execution.ExerciseExecutionEntity

@Dao
interface ExerciseExecutionDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertExecution(execution: ExerciseExecutionEntity)

    @Query("DELETE FROM exercise_execution_entity WHERE id = :id")
    suspend fun deleteExecutionById(id: String)

    @Query("DELETE FROM exercise_execution_entity WHERE exercise_id = :id")
    suspend fun deleteExecutionByExerciseId(id: String)
}