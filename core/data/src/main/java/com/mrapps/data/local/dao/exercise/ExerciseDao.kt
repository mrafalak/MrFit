package com.mrapps.data.local.dao.exercise

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: ExerciseEntity)

    @Query("SELECT * FROM exercise_entity WHERE type = :type ORDER BY name ASC")
    fun getExercisesByType(type: ExerciseTypeEnum): Flow<List<ExerciseEntity>>

    @Update
    suspend fun updateExercise(exercise: ExerciseEntity)

    @Query("DELETE FROM exercise_entity WHERE id = :id")
    suspend fun deleteExerciseById(id: String)

    @Query("DELETE FROM exercise_entity")
    suspend fun deleteAllExercises()
}