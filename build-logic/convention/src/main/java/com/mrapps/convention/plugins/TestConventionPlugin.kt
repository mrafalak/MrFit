package com.mrapps.convention.plugins

import com.mrapps.convention.Testing
import com.mrapps.convention.TestingAndroid
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.dsl.DependencyHandler

class TestConventionPlugin : LibConventionPlugin() {

    override fun DependencyHandler.TestingDependencies(versionCatalog: VersionCatalog) {
        Testing(versionCatalog)
        TestingAndroid(versionCatalog)
    }
}