import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.gradle)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApp") {
            id = libs.plugins.mrfit.application.get().pluginId
            implementationClass = "com.mrapps.convention.plugins.AppConventionPlugin"
        }
        register("androidAppCompose") {
            id = libs.plugins.mrfit.app.compose.get().pluginId
            implementationClass = "com.mrapps.convention.plugins.AppComposeConventionPlugin"
        }
        register("androidLib") {
            id = libs.plugins.mrfit.library.get().pluginId
            implementationClass = "com.mrapps.convention.plugins.LibConventionPlugin"
        }
        register("androidLibCompose") {
            id = libs.plugins.mrfit.lib.compose.get().pluginId
            implementationClass = "com.mrapps.convention.plugins.LibComposeConventionPlugin"
        }
        register("androidTest") {
            id = libs.plugins.mrfit.test.get().pluginId
            implementationClass = "com.mrapps.convention.plugins.TestConventionPlugin"
        }
    }
}