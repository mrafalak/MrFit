package com.mrapps.convention.plugins

import com.mrapps.convention.Room
import com.mrapps.convention.extensions.applyPluginsFromCatalog
import com.mrapps.convention.extensions.configureAndroidRoom
import com.mrapps.convention.extensions.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class RoomConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            val versionCatalog = versionCatalog()

            project.pluginManager.applyPluginsFromCatalog(
                project = project,
                catalog = versionCatalog,
                plugins = listOf("androidx-room")
            )

            dependencies {
                Room(versionCatalog)
            }

            configureAndroidRoom()
        }
    }
}