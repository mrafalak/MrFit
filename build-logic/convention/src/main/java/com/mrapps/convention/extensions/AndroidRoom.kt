package com.mrapps.convention.extensions

import androidx.room.gradle.RoomExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidRoom() {
    with(extensions.getByType<RoomExtension>()) {
        schemaDirectory("$rootDir/schemas")
    }
}