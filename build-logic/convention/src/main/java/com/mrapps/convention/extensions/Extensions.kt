package com.mrapps.convention.extensions

import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.PluginManager
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.use.PluginDependency

internal fun Project.versionCatalog(): VersionCatalog =
    extensions.getByType<VersionCatalogsExtension>().named("libs")

fun VersionCatalog.library(alias: String): Provider<MinimalExternalModuleDependency> =
    findLibrary(alias).orElseThrow {
        IllegalArgumentException("Library '$alias' not found in version catalog.")
    }

fun VersionCatalog.bundle(alias: String): Provider<ExternalModuleDependencyBundle> =
    findBundle(alias).orElseThrow {
        IllegalArgumentException("Bundle '$alias' not found in version catalog.")
    }

fun VersionCatalog.plugin(alias: String): Provider<PluginDependency> =
    findPlugin(alias).orElseThrow {
        IllegalArgumentException("Plugin '$alias' not found in version catalog.")
    }

fun PluginManager.applyPluginsFromCatalog(
    project: Project,
    catalog: VersionCatalog,
    plugins: List<String>
) {
    plugins.forEach { pluginId ->
        try {
            val plugin: PluginDependency = catalog.plugin(pluginId).get()

            apply(plugin.pluginId)
            project.logger.lifecycle("Successfully applied plugin: ${plugin.pluginId}")

        } catch (e: IllegalArgumentException) {
            project.logger.warn(e.message)

        } catch (e: Exception) {
            project.logger.error("Failed to apply plugin '$pluginId': ${e.message}", e)
        }
    }
}