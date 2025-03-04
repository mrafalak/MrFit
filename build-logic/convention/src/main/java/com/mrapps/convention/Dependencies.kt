package com.mrapps.convention

import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.Compose(versionCatalog: VersionCatalog, api: Boolean = false) {
    dependency(platform(versionCatalog.findLibrary("compose-bom").get()), api)
    implementation(versionCatalog.findBundle("ui-all").get())
}

fun DependencyHandler.LibTesting(versionCatalog: VersionCatalog) {
    testRuntimeOnly(versionCatalog.findLibrary("junit-jupiter-engine").get())
    testImplementation(versionCatalog.findBundle("testing").get())
}

fun DependencyHandler.LibTestingAndroid(versionCatalog: VersionCatalog, api: Boolean = false) {
    dependency(platform(versionCatalog.findLibrary("compose-bom").get()), api)
    androidTestImplementation(versionCatalog.findBundle("testing-android").get())
}

fun DependencyHandler.Testing(versionCatalog: VersionCatalog) {
    testRuntimeOnly(versionCatalog.findLibrary("junit-jupiter-engine").get())
    implementation(versionCatalog.findBundle("testing").get())
}

fun DependencyHandler.TestingAndroid(versionCatalog: VersionCatalog) {
    dependency(platform(versionCatalog.findLibrary("compose-bom").get()), false)
    implementation(versionCatalog.findBundle("testing-android").get())
}