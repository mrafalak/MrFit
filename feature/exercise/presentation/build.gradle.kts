import com.mrapps.convention.Config

plugins {
    alias(libs.plugins.mrfit.library)
    alias(libs.plugins.mrfit.lib.compose)
}

android {
    namespace = "${Config.android.namespace}.feature.exercise.presentation"
}

dependencies {
    implementation(projects.common.main)
    implementation(projects.core.domain)
    implementation(projects.core.navigation)
    implementation(projects.core.presentation)
    implementation(projects.feature.exercise.domain)
}