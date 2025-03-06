plugins {
    alias(libs.plugins.mrfit.application)
    alias(libs.plugins.mrfit.app.compose)
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.presentation)
    implementation(projects.core.navigation)
    implementation(projects.common.androidTesting)
    implementation(projects.common.main)
    implementation(projects.common.testing)
    implementation(projects.feature.exercise.presentation)
    implementation(projects.feature.home)
    implementation(projects.feature.settings)
}