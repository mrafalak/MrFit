package com.mrapps.convention.extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.gradle.LibraryExtension
import com.mrapps.convention.BuildTypeEnum
import com.mrapps.convention.Config.DEFAULT_PROGUARD_FILE
import com.mrapps.convention.Config.PROGUARD_RULES_FILE
import com.mrapps.convention.Config.buildConfigFlags
import org.gradle.api.Project

internal fun Project.configureAppBuildTypes(
    appExt: ApplicationExtension,
) {
    appExt.apply {
        buildTypes {
            getByName(BuildTypeEnum.RELEASE.value) {
                signingConfig = signingConfigs.getByName(BuildTypeEnum.RELEASE.value)
                isMinifyEnabled = true
                isShrinkResources = true
                proguardFiles(
                    getDefaultProguardFile(DEFAULT_PROGUARD_FILE),
                    file(PROGUARD_RULES_FILE)
                )
                addBuildConfigFlags(BuildTypeEnum.RELEASE)
            }
            getByName(BuildTypeEnum.DEBUG.value) {
                isMinifyEnabled = false
                isShrinkResources = false
                addBuildConfigFlags(BuildTypeEnum.DEBUG)
            }
        }
    }
}

internal fun Project.configureLibraryBuildTypes(
    libExt: LibraryExtension,
) {
    libExt.apply {
        buildTypes {
            getByName(BuildTypeEnum.RELEASE.value) {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile(DEFAULT_PROGUARD_FILE),
                    file(PROGUARD_RULES_FILE)
                )
                addBuildConfigFlags(BuildTypeEnum.RELEASE)
            }
            getByName(BuildTypeEnum.DEBUG.value) {
                isMinifyEnabled = false
                addBuildConfigFlags(BuildTypeEnum.DEBUG)
            }
        }
    }
}

private fun BuildType.addBuildConfigFlags(buildType: BuildTypeEnum) {
    buildConfigFlags.forEach { (flagName, flagValues) ->
        flagValues[buildType]?.let { value ->
            buildConfigField("boolean", flagName, value)
        }
    }
}