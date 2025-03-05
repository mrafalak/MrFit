import com.mrapps.convention.Config

plugins {
    alias(libs.plugins.mrfit.test)
}

android {
    namespace = "${Config.android.namespace}.common.testing"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
}