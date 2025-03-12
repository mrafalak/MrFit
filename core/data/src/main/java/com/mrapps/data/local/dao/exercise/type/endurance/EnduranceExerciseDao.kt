package com.mrapps.data.local.dao.exercise.type.endurance

import androidx.room.Dao
import androidx.room.Query
import com.mrapps.data.local.entity.exercise.type.EnduranceExerciseEntity

@Dao
interface EnduranceExerciseDao {

    @Query("SELECT * FROM endurance_exercise_entity WHERE exercise_id = :id")
    suspend fun getEnduranceExerciseById(id: String): EnduranceExerciseEntity
}