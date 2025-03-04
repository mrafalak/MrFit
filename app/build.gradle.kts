plugins {
    alias(libs.plugins.mrfit.application)
    alias(libs.plugins.mrfit.app.compose)
}

dependencies {
    implementation(projects.test)
    implementation(projects.common.navigation)
    implementation(projects.feature.home)
    implementation(projects.feature.settings)
}