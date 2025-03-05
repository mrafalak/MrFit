package com.mrapps.convention

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

private const val nameApplication = "MrFit"
private const val namespace = "com.mrapps.mrfit"
private const val versionNameMajor = 0
private const val versionNameMinor = 0
private const val versionNamePatch = 1

object Config {
    val android = AndroidConfig(
        minSdkVersion = 26,
        targetSdkVersion = 35,
        compileSdkVersion = 35,
        applicationId = namespace,
        versionCode = generateVersionCode(versionNameMajor, versionNameMinor, versionNamePatch),
        versionName = "$nameApplication $versionNameMajor.$versionNameMinor.$versionNamePatch",
        namespace = namespace,
    )
    val jvm = JvmConfig(
        javaVersion = JavaVersion.VERSION_17,
        kotlinJvm = JvmTarget.JVM_17,
        freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    )
    val buildConfigFlags: Map<String, Map<BuildTypeEnum, String>> = mapOf()
    val resourcesExcludes: Set<String> = setOf(
        "META-INF/LICENSE.md",
        "META-INF/LICENSE-notice.md",
    )
    const val DEFAULT_PROGUARD_FILE = "proguard-android-optimize.txt"
    const val PROGUARD_RULES_FILE = "proguard-rules.pro"
    const val KOTLIN_COMPILER_EXT_VERSION = "1.5.15"
}

data class AndroidConfig(
    val minSdkVersion: Int,
    val targetSdkVersion: Int,
    val compileSdkVersion: Int,
    val applicationId: String,
    val versionCode: Int,
    val versionName: String,
    val namespace: String,
)

data class JvmConfig(
    val javaVersion: JavaVersion,
    val kotlinJvm: JvmTarget,
    val freeCompilerArgs: List<String>
)

fun generateVersionCode(versionNameMajor: Int, versionNameMinor: Int, versionNamePatch: Int): Int {
    return versionNameMajor * 10000 + versionNameMinor * 100 + versionNamePatch
}