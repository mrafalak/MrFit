import com.mrapps.convention.Config

plugins {
    alias(libs.plugins.mrfit.library)
    alias(libs.plugins.mrfit.lib.compose)
}

android {
    namespace = "${Config.android.namespace}.core.presentation"
}

dependencies {
    implementation(projects.common.main)
    implementation(projects.core.domain)
}