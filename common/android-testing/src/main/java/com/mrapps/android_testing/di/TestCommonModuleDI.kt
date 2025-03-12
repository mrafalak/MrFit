package com.mrapps.android_testing.di

import com.mrapps.android_testing.FakeTimeProvider
import com.mrapps.main.di.CommonModuleDI
import com.mrapps.main.time.TimeProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CommonModuleDI::class]
)
abstract class TestCommonModuleDI {

    @Binds
    abstract fun timeProvider(provider: FakeTimeProvider): TimeProvider
}