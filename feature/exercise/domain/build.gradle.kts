import com.mrapps.convention.Config

plugins {
    alias(libs.plugins.mrfit.library)
}

android {
    namespace = "${Config.android.namespace}.feature.exercise.domain"
}

dependencies {
    implementation(projects.common.main)
    implementation(projects.core.domain)
}