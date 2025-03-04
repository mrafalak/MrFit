package com.mrapps.settings.di

import com.mrapps.navigation.FeatureNavGraph
import com.mrapps.settings.navigation.SettingsNavGraph
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    @IntoSet
    abstract fun bindSettingsNavGraph(settingsNavGraph: SettingsNavGraph): FeatureNavGraph
}