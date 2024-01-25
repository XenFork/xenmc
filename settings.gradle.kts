//import io.github.xenfork.xenmc.gradle.extensions.MinecraftExtension
//import org.gradle.api.internal.catalog.VersionCatalogView
//import kotlin.math.min

pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri(".gradle/build/maven")
        }
    }
}


//plugins {
//    id("io.github.xenfork.xenmc.plugin").version("1.0.0-SNAPSHOT")
//}

rootProject.name = "xen-mc"

include("plugin", "loader")
