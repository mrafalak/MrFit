plugins {
    alias(libs.plugins.mrfit.application)
    alias(libs.plugins.mrfit.app.compose)
}

dependencies {
    implementation(projects.common.testing)
    implementation(projects.common.androidTesting)
    implementation(projects.common.data)
    implementation(projects.common.domain)
    implementation(projects.common.navigation)
    implementation(projects.common.presentation)
    implementation(projects.common.main)
    implementation(projects.feature.home)
    implementation(projects.feature.settings)
}