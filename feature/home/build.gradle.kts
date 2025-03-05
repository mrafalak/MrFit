import com.mrapps.convention.Config

plugins {
    alias(libs.plugins.mrfit.library)
    alias(libs.plugins.mrfit.lib.compose)
}

android {
    namespace = "${Config.android.namespace}.feature.home"
}

dependencies {
    implementation(projects.core.navigation)
}