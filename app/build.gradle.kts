plugins {
    alias(libs.plugins.mrfit.application)
    alias(libs.plugins.mrfit.app.compose)
}

dependencies {
    implementation(projects.test)
    implementation(projects.common.navigation)
    implementation(projects.common.data)
    implementation(projects.common.domain)
    implementation(projects.common.presentation)
    implementation(projects.common.util)
    implementation(projects.feature.home)
    implementation(projects.feature.settings)
}