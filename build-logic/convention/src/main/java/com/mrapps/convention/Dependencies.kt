package com.mrapps.convention

import com.mrapps.convention.extensions.bundle
import com.mrapps.convention.extensions.library
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.Compose(versionCatalog: VersionCatalog, api: Boolean = false) {
    dependency(platform(versionCatalog.library("compose-bom")), api)
    dependency(versionCatalog.bundle("ui-all"), api)
}

fun DependencyHandler.Testing(versionCatalog: VersionCatalog) {
    testRuntimeOnly(versionCatalog.library("junit-jupiter-engine"))
    testImplementation(versionCatalog.bundle("testing"))
}

fun DependencyHandler.TestingAndroid(versionCatalog: VersionCatalog, api: Boolean = false) {
    dependency(platform(versionCatalog.library("compose-bom")), api)
    androidTestImplementation(versionCatalog.bundle("testing-android"))
    androidTestImplementation(versionCatalog.library("hilt-android-test"))
    kspAndroidTest(versionCatalog.library("hilt-android-compiler"))
}

fun DependencyHandler.Hilt(versionCatalog: VersionCatalog, api: Boolean = false) {
    dependency(versionCatalog.library("hilt-android"), api)
    ksp(versionCatalog.library("hilt-android-compiler"))
}

fun DependencyHandler.Room(versionCatalog: VersionCatalog, api: Boolean = false) {
    dependency(versionCatalog.bundle("room"), api)
    ksp(versionCatalog.library("room-compiler"))
}