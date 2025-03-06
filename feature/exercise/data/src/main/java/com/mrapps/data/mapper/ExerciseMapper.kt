package com.mrapps.data.mapper

import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.entity.exercise.type.StrengthExerciseEntity
import com.mrapps.data.local.relation.ExerciseWithStrengthExercise
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.model.exercise_type.ExerciseType

fun Exercise.toExerciseWithStrengthEntities(): Pair<ExerciseEntity, StrengthExerciseEntity> {
    if (this.type !is ExerciseType.Strength) {
        throw IllegalArgumentException()
    } else {
        val type = this.type as ExerciseType.Strength

        val exerciseEntity = ExerciseEntity(
            id = this.id,
            name = this.name,
            type = this.type.type,
            description = this.description
        )


        val strengthExerciseEntity = StrengthExerciseEntity(
            exerciseId = this.id,
            movementType = type.movementType,
            muscleGroup = type.muscleGroup,
            exerciseGoal = type.exerciseGoal
        )

        return Pair(exerciseEntity, strengthExerciseEntity)
    }
}

fun ExerciseWithStrengthExercise.toExercise(): Exercise {
    if (this.exercise.type != ExerciseTypeEnum.STRENGTH) {
        throw IllegalArgumentException()
    } else {
        return Exercise(
            id = this.exercise.id,
            name = this.exercise.name,
            type = ExerciseType.Strength(
                movementType = this.strengthExercise.movementType,
                muscleGroup = this.strengthExercise.muscleGroup,
                exerciseGoal = this.strengthExercise.exerciseGoal
            ),
            description = this.exercise.description
        )
    }
}