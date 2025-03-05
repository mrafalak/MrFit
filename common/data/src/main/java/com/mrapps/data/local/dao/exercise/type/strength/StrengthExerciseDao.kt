package com.mrapps.data.local.dao.exercise.type.strength

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mrapps.data.local.entity.exercise.type.strength.StrengthExerciseEntity

@Dao
interface StrengthExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStrengthExercise(exercise: StrengthExerciseEntity)

    @Update
    suspend fun updateStrengthExercise(exercise: StrengthExerciseEntity)

    @Query("DELETE FROM strength_exercise_entity WHERE exercise_id = :id")
    suspend fun deleteStrengthExerciseById(id: String)
}