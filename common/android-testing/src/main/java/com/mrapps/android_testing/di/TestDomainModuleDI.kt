package com.mrapps.android_testing.di

import com.mrapps.domain.di.DomainModuleDI
import com.mrapps.domain.util.DispatcherProvider
import com.mrapps.test.TestDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DomainModuleDI::class]
)
object TestDomainModuleDI {

    @Provides
    fun provideTestDispatcherProvider(): DispatcherProvider {
        return TestDispatchers()
    }
}