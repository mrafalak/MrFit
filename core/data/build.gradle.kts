import com.mrapps.convention.Config

plugins {
    alias(libs.plugins.mrfit.library)
    alias(libs.plugins.mrfit.room)
}

android {
    namespace = "${Config.android.namespace}.core.data"
}

dependencies {
    implementation(projects.common.main)
    implementation(projects.core.domain)
}