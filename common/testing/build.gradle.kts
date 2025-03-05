import com.mrapps.convention.Config

plugins {
    alias(libs.plugins.mrfit.test)
}

android {
    namespace = "${Config.android.namespace}.common.testing"
}

dependencies {
    implementation(projects.common.data)
    implementation(projects.common.domain)
}