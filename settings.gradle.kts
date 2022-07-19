pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
rootProject.name = "HabitTracker-Compose"
rootProject.buildFileName = "build.gradle.kts"
include(":app",
    ":api",
    ":core",
    ":feature-habitlist-api",
    ":feature-habitlist-impl",
    ":feature-habiteditor-api",
    ":feature-habiteditor-impl",
    ":feature-settings-api",
    ":feature-settings-impl")
