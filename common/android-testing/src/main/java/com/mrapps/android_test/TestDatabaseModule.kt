package com.mrapps.android_test

import android.content.Context
import androidx.room.Room
import com.mrapps.data.local.AppDatabase
import com.mrapps.data.local.DatabaseModule
import com.mrapps.data.local.dao.exercise.ExerciseDao
import com.mrapps.data.local.dao.exercise.ExerciseExecutionDao
import com.mrapps.data.local.dao.exercise.type.strength.StrengthExerciseDao
import com.mrapps.data.local.dao.exercise.type.strength.StrengthSetDao
import com.mrapps.data.local.dao.training.TrainingExerciseDao
import com.mrapps.data.local.dao.training.TrainingSessionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object TestDatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()

    @Provides
    fun provideExerciseDao(appDatabase: AppDatabase): ExerciseDao = appDatabase.exerciseDao()

    @Provides
    fun provideExerciseExecutionDao(appDatabase: AppDatabase): ExerciseExecutionDao =
        appDatabase.exerciseExecutionDao()

    @Provides
    fun provideStrengthExerciseDao(appDatabase: AppDatabase): StrengthExerciseDao =
        appDatabase.strengthExerciseDao()

    @Provides
    fun provideStrengthSetDao(appDatabase: AppDatabase): StrengthSetDao =
        appDatabase.strengthSetDao()

    @Provides
    fun provideTrainingSessionDao(appDatabase: AppDatabase): TrainingSessionDao =
        appDatabase.trainingSessionDao()

    @Provides
    fun provideTrainingExerciseDao(appDatabase: AppDatabase): TrainingExerciseDao =
        appDatabase.trainingExerciseDao()
}