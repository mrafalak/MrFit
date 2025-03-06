package com.mrapps.convention.plugins

import com.android.build.gradle.LibraryExtension
import com.mrapps.convention.Config
import com.mrapps.convention.Hilt
import com.mrapps.convention.Testing
import com.mrapps.convention.extensions.configureAndroidKotlin
import com.mrapps.convention.extensions.configureLibraryBuildTypes
import com.mrapps.convention.extensions.versionCatalog
import com.mrapps.convention.implementation
import com.mrapps.convention.TestingAndroid
import com.mrapps.convention.extensions.applyPluginsFromCatalog
import com.mrapps.convention.extensions.library
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

abstract class LibConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            val versionCatalog = versionCatalog()

            project.pluginManager.applyPluginsFromCatalog(
                project = project,
                catalog = versionCatalog,
                plugins = listOf(
                    "android-library",
                    "kotlin-android",
                    "hilt-android-plugin",
                    "ksp"
                )
            )

            dependencies {
                implementation(versionCatalog.library("androidx-core-ktx"))
                implementation(versionCatalog.library("timber-core"))
                Hilt(versionCatalog)
                TestingDependencies(versionCatalog)
            }

            extensions.configure<LibraryExtension> {
                configureAndroidKotlin(this)
                configureLibraryBuildTypes(this)

                defaultConfig.targetSdk = Config.android.targetSdkVersion
                defaultConfig.testInstrumentationRunner = Config.android.testInstrumentationRunner

                buildFeatures.buildConfig = true

                packaging {
                    resources {
                        excludes += Config.resourcesExcludes
                    }
                }
            }

            tasks.withType<Test> {
                useJUnitPlatform()
                tasks.withType<Test> {
                    useJUnitPlatform()
                    inputs.files(fileTree("src/test"))
                    outputs.dir(layout.buildDirectory.dir("test-results"))
                }
            }
        }
    }

    protected open fun DependencyHandler.TestingDependencies(versionCatalog: VersionCatalog) {
        Testing(versionCatalog)
        TestingAndroid(versionCatalog)
    }
}