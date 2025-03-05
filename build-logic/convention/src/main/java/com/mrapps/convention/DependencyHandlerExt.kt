package com.mrapps.convention

import org.gradle.api.artifacts.dsl.DependencyHandler

internal fun DependencyHandler.dependency(dependency: Any, api: Boolean) {
    if (api) api(dependency) else implementation(dependency)
}

internal fun DependencyHandler.implementation(dependency: Any) {
    this.add("implementation", dependency)
}

internal fun DependencyHandler.ksp(dependency: Any) {
    this.add("ksp", dependency)
}

internal fun DependencyHandler.kspAndroidTest(dependency: Any) {
    this.add("kspAndroidTest", dependency)
}

internal fun DependencyHandler.api(dependency: Any) {
    this.add("api", dependency)
}

internal fun DependencyHandler.testImplementation(value: Any) {
    this.add("testImplementation", value)
}

internal fun DependencyHandler.androidTestImplementation(value: Any) {
    this.add("androidTestImplementation", value)
}

internal fun DependencyHandler.testRuntimeOnly(value: Any) {
    this.add("testRuntimeOnly", value)
}