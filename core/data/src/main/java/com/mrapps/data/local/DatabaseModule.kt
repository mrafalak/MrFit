package com.mrapps.data.local

import android.content.Context
import androidx.room.Room
import com.mrapps.data.local.dao.exercise.ExerciseDao
import com.mrapps.data.local.dao.exercise.ExerciseExecutionDao
import com.mrapps.data.local.dao.exercise.type.endurance.EnduranceExerciseDao
import com.mrapps.data.local.dao.exercise.type.strength.StrengthExerciseDao
import com.mrapps.data.local.dao.exercise.type.strength.StrengthSetDao
import com.mrapps.data.local.dao.training.TrainingExerciseDao
import com.mrapps.data.local.dao.training.TrainingSessionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            LocalDataConfig.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

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
    fun provideEnduranceExerciseDao(appDatabase: AppDatabase): EnduranceExerciseDao =
        appDatabase.enduranceExerciseDao()

    @Provides
    fun provideTrainingSessionDao(appDatabase: AppDatabase): TrainingSessionDao =
        appDatabase.trainingSessionDao()

    @Provides
    fun provideTrainingExerciseDao(appDatabase: AppDatabase): TrainingExerciseDao =
        appDatabase.trainingExerciseDao()
}