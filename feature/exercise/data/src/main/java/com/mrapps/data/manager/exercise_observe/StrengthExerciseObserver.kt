package com.mrapps.data.manager.exercise_observe

import com.mrapps.data.local.dao.exercise.type.strength.StrengthExerciseDao
import com.mrapps.data.local.util.safeDatabaseFlowOperation
import com.mrapps.data.mapper.toExercise
import com.mrapps.domain.DataError
import com.mrapps.domain.Result
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.util.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StrengthExerciseObserver @Inject constructor(
    private val strengthExerciseDao: StrengthExerciseDao,
    private val dispatchers: DispatcherProvider
) : ExerciseObserver {

    override fun observe(): Flow<Result<List<Exercise>, DataError.Local>> {
        return safeDatabaseFlowOperation<List<Exercise>, StrengthExerciseObserver>(
            dispatcher = dispatchers.io
        ) {
            strengthExerciseDao.observeStrengthExercises().map { entityList ->
                entityList.map { entity ->
                    entity.toExercise()
                }
            }
        }
    }
}