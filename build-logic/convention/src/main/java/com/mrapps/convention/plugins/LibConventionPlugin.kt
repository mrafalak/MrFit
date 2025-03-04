package com.mrapps.convention.plugins

import com.android.build.gradle.LibraryExtension
import com.mrapps.convention.Config
import com.mrapps.convention.LibTesting
import com.mrapps.convention.extensions.configureAndroidKotlin
import com.mrapps.convention.extensions.configureLibraryBuildTypes
import com.mrapps.convention.extensions.versionCatalog
import com.mrapps.convention.implementation
import com.mrapps.convention.LibTestingAndroid
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
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.devtools.ksp")
            }

            dependencies {
                implementation(versionCatalog().findLibrary("androidx-core-ktx").get())
                TestingDependencies(versionCatalog())
            }

            extensions.configure<LibraryExtension> {
                configureAndroidKotlin(this)
                configureLibraryBuildTypes(this)

                defaultConfig.targetSdk = Config.android.targetSdkVersion

                buildFeatures.buildConfig = true

                packaging {
                    resources {
                        excludes += setOf(
                            "META-INF/LICENSE.md",
                            "META-INF/LICENSE-notice.md",
                        )
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
        LibTesting(versionCatalog)
        LibTestingAndroid(versionCatalog)
    }
}