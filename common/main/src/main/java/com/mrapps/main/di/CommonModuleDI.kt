package com.mrapps.main.di

import com.mrapps.main.time.DeviceTimeProvider
import com.mrapps.main.time.TimeProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonModuleDI {

    @Singleton
    @Binds
    abstract fun timeProvider(provider: DeviceTimeProvider): TimeProvider
}