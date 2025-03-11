package com.mrapps.domain.use_case

import com.mrapps.domain.DataError
import com.mrapps.domain.Result
import com.mrapps.domain.handler.ExerciseHandlerFactory
import com.mrapps.domain.model.Exercise
import com.mrapps.main.util.log.error
import timber.log.Timber
import javax.inject.Inject

class AddNewExerciseUseCase @Inject constructor(
    private val exerciseHandlerFactory: ExerciseHandlerFactory
) {
    suspend fun invoke(exercise: Exercise): Result<Unit, DataError.Local> {
        return runCatching {
            exerciseHandlerFactory.getHandler(exercise.type.type)
        }.mapCatching { handler ->
            handler.addExercise(exercise)
        }.getOrElse { e ->
            Timber.error<AddNewExerciseUseCase>(e.message, e)
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}