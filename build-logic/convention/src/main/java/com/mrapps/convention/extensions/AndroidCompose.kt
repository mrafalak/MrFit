package com.mrapps.convention.extensions

import com.android.build.api.dsl.CommonExtension
import com.mrapps.convention.Config
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

internal fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    commonExtension.apply {
        buildFeatures.compose = true
    }

    commonExtension.composeOptions {
        kotlinCompilerExtensionVersion = Config.KOTLIN_COMPILER_EXT_VERSION
    }

    extensions.configure<ComposeCompilerGradlePluginExtension> {
        includeSourceInformation.set(true)
    }
}