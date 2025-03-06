import com.mrapps.convention.Config

plugins {
    alias(libs.plugins.mrfit.test)
}

android {
    namespace = "${Config.android.namespace}.feature.exercise.testing"
}

dependencies {
    implementation(projects.common.main)
    implementation(projects.core.domain)
    implementation(projects.feature.exercise.domain)
}