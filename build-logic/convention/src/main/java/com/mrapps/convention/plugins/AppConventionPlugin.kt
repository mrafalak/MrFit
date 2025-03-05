package com.mrapps.convention.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.mrapps.convention.BuildTypeEnum
import com.mrapps.convention.Config
import com.mrapps.convention.Hilt
import com.mrapps.convention.Testing
import com.mrapps.convention.TestingAndroid
import com.mrapps.convention.extensions.applyPluginsFromCatalog
import com.mrapps.convention.extensions.configureAndroidKotlin
import com.mrapps.convention.extensions.configureAppBuildTypes
import com.mrapps.convention.extensions.library
import com.mrapps.convention.extensions.versionCatalog
import com.mrapps.convention.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import java.util.Properties

class AppConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            val versionCatalog = versionCatalog()

            project.pluginManager.applyPluginsFromCatalog(
                project = project,
                catalog = versionCatalog,
                plugins = listOf(
                    "android-application",
                    "kotlin-android",
                    "hilt-android-plugin",
                    "ksp"
                )
            )

            dependencies {
                implementation(versionCatalog.library("androidx-core-ktx"))
                implementation(versionCatalog.library("timber-core"))
                Hilt(versionCatalog)
                Testing(versionCatalog)
                TestingAndroid(versionCatalog)
            }

            extensions.configure<ApplicationExtension> {
                val keystoreFile = project.rootProject.file("local.properties")
                val properties = Properties()
                properties.load(keystoreFile.inputStream())

                signingConfigs {
                    create(BuildTypeEnum.RELEASE.value) {
                        storeFile = file(properties["KEYSTORE_PATH"] as String)
                        storePassword = properties["KEYSTORE_PASSWORD"] as String
                        keyAlias = properties["KEY_ALIAS"] as String
                        keyPassword = properties["KEY_PASSWORD"] as String
                    }
                }

                configureAndroidKotlin(this)
                configureAppBuildTypes(this)

                defaultConfig {
                    namespace = Config.android.namespace
                    applicationId = Config.android.applicationId
                    targetSdk = Config.android.targetSdkVersion
                    versionCode = Config.android.versionCode
                    versionName = Config.android.versionName

                    testInstrumentationRunner = "com.mrapps.test.HiltTestRunner"
                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }

                buildFeatures.buildConfig = true

                packaging {
                    resources {
                        excludes += Config.resourcesExcludes
                    }
                }

                tasks.withType<Test> {
                    useJUnitPlatform()
                    inputs.files(fileTree("src/test"))
                    outputs.dir(layout.buildDirectory.dir("test-results"))
                }
            }
        }
    }
}