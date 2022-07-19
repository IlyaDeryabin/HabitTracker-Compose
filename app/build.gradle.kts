import extensions.*
import dependencies.*

plugins {
    id(BuildPlugins.ANDROID_APPLICATION)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.HILT)
}

android {
    compileSdk = BuildAndroidConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = BuildAndroidConfig.APPLICATION_ID
        minSdk = BuildAndroidConfig.MIN_SDK_VERSION
        targetSdk = BuildAndroidConfig.TARGET_SDK_VERSION
        versionCode = BuildAndroidConfig.VERSION_CODE
        versionName = BuildAndroidConfig.VERSION_NAME

        testInstrumentationRunner = BuildAndroidConfig.TEST_INSTRUMENTATION_RUNNER
        vectorDrawables {
            useSupportLibrary = BuildAndroidConfig.SUPPORT_LIBRARY_VECTOR_DRAWABLES
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = DependenciesVersions.Compose.COMPOSE_COMPILER
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(BuildModules.CORE))
    implementation(project(BuildModules.API))
    implementation(project(BuildModules.Features.HABIT_LIST_API))
    implementation(project(BuildModules.Features.HABIT_LIST))
    implementation(project(BuildModules.Features.HABIT_EDITOR_API))
    implementation(project(BuildModules.Features.HABIT_EDITOR))
    implementation(project(BuildModules.Features.SETTINGS_API))
    implementation(project(BuildModules.Features.SETTINGS_IMPL))

    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.MATERIAL)

    /* Compose */
    implementation(Dependencies.Compose.ACTIVITY)
    implementation(Dependencies.Compose.UI)
    implementation(Dependencies.Compose.MATERIAL)
    implementation(Dependencies.Compose.PREVIEW)

    // Navigation
    implementation(Dependencies.Compose.NAVIGATION)
    implementation(Dependencies.Compose.HILT_NAVIGATION)

    // Accompanist
    implementation(Dependencies.Accompanist.NAVIGATION_ANIMATION)

    /* Dagger Hilt */
    implementation(Dependencies.HILT)
    kapt(AnnotationProcessorDependencies.HILT)

    /* Test */
    testImplementation(TestDependencies.JUNIT)
    androidTestImplementation(AndroidTestDependencies.JUNIT)
    androidTestImplementation(AndroidTestDependencies.ESPRESSO)
    androidTestImplementation(AndroidTestDependencies.COMPOSE_JUNIT)
    debugImplementation(DebugDependencies.COMPOSE_UI_TOOLING)
}