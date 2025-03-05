package com.mrapps.convention.plugins

import com.mrapps.convention.dependency
import com.mrapps.convention.extensions.bundle
import com.mrapps.convention.extensions.library
import com.mrapps.convention.implementation
import com.mrapps.convention.ksp
import com.mrapps.convention.testRuntimeOnly
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.dsl.DependencyHandler

class TestConventionPlugin : LibConventionPlugin() {

    override fun DependencyHandler.TestingDependencies(versionCatalog: VersionCatalog) {
        Testing(versionCatalog)
        TestingAndroid(versionCatalog)
    }

    private fun DependencyHandler.Testing(versionCatalog: VersionCatalog) {
        testRuntimeOnly(versionCatalog.library("junit-jupiter-engine"))
        implementation(versionCatalog.bundle("testing"))
    }

    private fun DependencyHandler.TestingAndroid(versionCatalog: VersionCatalog) {
        dependency(platform(versionCatalog.library("compose-bom")), false)
        implementation(versionCatalog.bundle("testing-android"))
        implementation(versionCatalog.library("hilt-android-test"))
        ksp(versionCatalog.library("hilt-android-compiler"))
    }
}