import com.mrapps.convention.Config

plugins {
    alias(libs.plugins.mrfit.library)
    alias(libs.plugins.mrfit.lib.compose)
}

android {
    namespace = "${Config.android.namespace}.feature.exercise.presentation"
}

dependencies {
    implementation(projects.feature.exercise.domain)
}