package com.mrapps.domain.handler

import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import javax.inject.Inject

class ExerciseHandlerFactory @Inject constructor(
    private val handlers: Set<@JvmSuppressWildcards ExerciseHandler>
) {
    fun getHandler(exerciseType: ExerciseTypeEnum): ExerciseHandler {
        return handlers.firstOrNull { it.supports(exerciseType) }
            ?: throw IllegalArgumentException("No handler found for exercise type: $exerciseType")
    }
}