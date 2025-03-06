import com.mrapps.convention.Config

plugins {
    alias(libs.plugins.mrfit.library)
    alias(libs.plugins.mrfit.room)
}

android {
    namespace = "${Config.android.namespace}.feature.exercise.data"
}

dependencies {
    implementation(projects.common.androidTesting)
    implementation(projects.common.testing)
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.feature.exercise.domain)
    implementation(projects.feature.exercise.testing)
}