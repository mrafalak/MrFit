package com.mrapps.data.manager.exercise_get_list

import com.mrapps.domain.DataError
import com.mrapps.domain.Result
import com.mrapps.domain.model.Exercise

interface ExerciseListGetter {
    suspend fun getList(): Result<List<Exercise>, DataError.Local>
}