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
    ":feature-habitlist",
    ":feature-habiteditor",
    ":feature-habitlist-api",
    ":feature-habiteditor-api")
