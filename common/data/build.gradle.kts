import com.mrapps.convention.Config

plugins {
    alias(libs.plugins.mrfit.library)
}

android {
    namespace = "${Config.android.namespace}.common.data"
}

dependencies {
    implementation(projects.common.domain)
    implementation(projects.common.util)
}