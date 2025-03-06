package com.mrapps.presentation.di

import com.mrapps.navigation.FeatureNavGraph
import com.mrapps.presentation.navigation.ExerciseNavGraph
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class ExercisePresentationModule {

    @Binds
    @IntoSet
    abstract fun bindExerciseNavGraph(exerciseNavGraph: ExerciseNavGraph): FeatureNavGraph
}