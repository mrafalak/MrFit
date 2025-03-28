package com.mrapps.data.local.dao.exercise

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mrapps.data.local.entity.exercise.ExerciseEntity

@Dao
interface ExerciseDao : BaseExerciseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun insertExercise(exercise: ExerciseEntity)

    @Update
    override suspend fun updateExercise(exercise: ExerciseEntity)

    @Query("DELETE FROM exercise_entity WHERE id = :id")
    override suspend fun deleteExerciseById(id: String)

    @Query("SELECT * FROM exercise_entity ORDER BY name ASC")
    suspend fun getAllExercises(): List<ExerciseEntity>

    @Query("SELECT * FROM exercise_entity WHERE id = :id")
    suspend fun getExerciseById(id: String): ExerciseEntity

    @Query("SELECT * FROM exercise_entity WHERE name = :name")
    suspend fun getExerciseByName(name: String): ExerciseEntity?
}