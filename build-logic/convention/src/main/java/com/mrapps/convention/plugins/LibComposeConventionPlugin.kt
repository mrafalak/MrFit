package com.mrapps.convention.plugins

import com.android.build.api.dsl.LibraryExtension
import com.mrapps.convention.Compose
import com.mrapps.convention.extensions.configureAndroidCompose
import com.mrapps.convention.extensions.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class LibComposeConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            val versionCatalog = versionCatalog()

            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            dependencies {
                Compose(versionCatalog)
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}