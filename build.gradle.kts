buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${DependenciesVersions.KOTLIN}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${DependenciesVersions.HILT}")
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(BuildPlugins.ANDROID_APPLICATION) version "7.2.1" apply false
    id(BuildPlugins.ANDROID_LIBRARY) version "7.2.1" apply false
    id(BuildPlugins.KOTLIN_ANDROID) version DependenciesVersions.KOTLIN apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}