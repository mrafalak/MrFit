package com.mrapps.data.local.dao.exercise.type.strength

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mrapps.data.local.entity.exercise.execution.StrengthSetEntity

@Dao
interface StrengthSetDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertStrengthSet(set: StrengthSetEntity)

    @Update
    suspend fun updateStrengthSet(set: StrengthSetEntity)

    @Query("DELETE FROM strength_set_entity WHERE id = :id")
    suspend fun deleteStrengthSetById(id: String)

    @Query("DELETE FROM strength_set_entity WHERE exercise_execution_id = :id")
    suspend fun deleteStrengthSetByExerciseExecutionId(id: String)
}