package com.mrapps.android_testing.di

import com.mrapps.android_testing.FakeTimeProvider
import com.mrapps.domain.di.DomainModuleDI
import com.mrapps.main.time.TimeProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DomainModuleDI::class]
)
abstract class TestDomainModuleDI {

    @Binds
    abstract fun timeProvider(provider: FakeTimeProvider): TimeProvider
}