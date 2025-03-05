import com.mrapps.convention.Config

plugins {
    alias(libs.plugins.mrfit.test)
    alias(libs.plugins.mrfit.room)
}

android {
    namespace = "${Config.android.namespace}.common.androidtesting"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.common.testing)
    implementation(projects.common.main)
}