package com.mrapps.feature.home.screen.di

import com.mrapps.feature.home.screen.navigation.HomeNavGraph
import com.mrapps.navigation.FeatureNavGraph
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {

    @Binds
    @IntoSet
    abstract fun bindHomeNavGraph(homeNavGraph: HomeNavGraph): FeatureNavGraph
}