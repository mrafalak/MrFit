import com.mrapps.convention.Config

plugins {
    alias(libs.plugins.mrfit.test)
}

android {
    namespace = "${Config.android.namespace}.test"
}