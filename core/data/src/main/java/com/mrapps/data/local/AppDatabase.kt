package com.mrapps.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mrapps.data.local.dao.exercise.ExerciseDao
import com.mrapps.data.local.dao.exercise.ExerciseExecutionDao
import com.mrapps.data.local.dao.exercise.type.endurance.EnduranceExerciseDao
import com.mrapps.data.local.dao.exercise.type.strength.StrengthExerciseDao
import com.mrapps.data.local.dao.exercise.type.strength.StrengthSetDao
import com.mrapps.data.local.dao.training.TrainingExerciseDao
import com.mrapps.data.local.dao.training.TrainingSessionDao
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.entity.exercise.execution.ExerciseExecutionEntity
import com.mrapps.data.local.entity.exercise.type.StrengthExerciseEntity
import com.mrapps.data.local.entity.exercise.execution.StrengthSetEntity
import com.mrapps.data.local.entity.exercise.type.EnduranceExerciseEntity
import com.mrapps.data.local.entity.training.TrainingExerciseEntity
import com.mrapps.data.local.entity.training.TrainingSessionEntity

@Database(
    entities = [
        ExerciseEntity::class,
        ExerciseExecutionEntity::class,
        StrengthExerciseEntity::class,
        StrengthSetEntity::class,
        EnduranceExerciseEntity::class,
        TrainingSessionEntity::class,
        TrainingExerciseEntity::class
    ],
    version = LocalDataConfig.DATABASE_VERSION
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun exerciseExecutionDao(): ExerciseExecutionDao
    abstract fun strengthExerciseDao(): StrengthExerciseDao
    abstract fun strengthSetDao(): StrengthSetDao
    abstract fun enduranceExerciseDao(): EnduranceExerciseDao
    abstract fun trainingSessionDao(): TrainingSessionDao
    abstract fun trainingExerciseDao(): TrainingExerciseDao
}