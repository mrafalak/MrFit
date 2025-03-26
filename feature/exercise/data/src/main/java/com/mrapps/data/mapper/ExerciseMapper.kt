package com.mrapps.data.mapper

import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.entity.exercise.type.EnduranceExerciseEntity
import com.mrapps.data.local.entity.exercise.type.StrengthExerciseEntity
import com.mrapps.data.local.relation.ExerciseWithType
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.model.exercise_type.ExerciseType
import com.mrapps.domain.units.MeasurementUnit

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

fun Exercise.toExerciseWithEnduranceEntities(): Pair<ExerciseEntity, EnduranceExerciseEntity> {
    if (this.type !is ExerciseType.Endurance) {
        throw IllegalArgumentException()
    } else {
        val type = this.type as ExerciseType.Endurance

        val exerciseEntity = ExerciseEntity(
            id = this.id,
            name = this.name,
            type = this.type.type,
            description = this.description
        )

        val enduranceExerciseEntity = EnduranceExerciseEntity(
            exerciseId = this.id,
            activityType = type.activityType,
            durationUnitId = type.durationUnit?.id,
        )

        return Pair(exerciseEntity, enduranceExerciseEntity)
    }
}

fun ExerciseWithType.Strength.toExercise(): Exercise {
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

fun ExerciseWithType.Endurance.toExercise(): Exercise {
    if (this.exercise.type != ExerciseTypeEnum.ENDURANCE) {
        throw IllegalArgumentException()
    } else {
        val durationUnit = MeasurementUnit.Time.fromId(this.enduranceExercise.durationUnitId)

        return Exercise(
            id = this.exercise.id,
            name = this.exercise.name,
            type = ExerciseType.Endurance(
                activityType = this.enduranceExercise.activityType,
                durationUnit = durationUnit,
            ),
            description = this.exercise.description
        )
    }
}