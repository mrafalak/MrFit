plugins {
    alias(libs.plugins.mrfit.application)
    alias(libs.plugins.mrfit.app.compose)
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.navigation)
    implementation(projects.core.presentation)
    implementation(projects.common.androidTesting)
    implementation(projects.common.main)
    implementation(projects.common.testing)
    implementation(projects.feature.home)
    implementation(projects.feature.settings)
}