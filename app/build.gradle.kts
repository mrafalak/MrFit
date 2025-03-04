plugins {
    alias(libs.plugins.mrfit.application)
    alias(libs.plugins.mrfit.app.compose)
}

dependencies {
    implementation(projects.test)
}