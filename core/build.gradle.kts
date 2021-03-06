import dependencies.*
import extensions.*

plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
    id(BuildPlugins.KOTLIN_ANDROID)
}

android {
    compileSdk = BuildAndroidConfig.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = BuildAndroidConfig.MIN_SDK_VERSION
        targetSdk = BuildAndroidConfig.TARGET_SDK_VERSION

        testInstrumentationRunner = BuildAndroidConfig.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFile("consumer-rules.pro")
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
    composeOptions {
        kotlinCompilerExtensionVersion = DependenciesVersions.Compose.COMPOSE_COMPILER
    }
    buildFeatures {
        compose = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    // Compose
    implementation(Dependencies.Compose.UI)
    implementation(Dependencies.Compose.MATERIAL)
    implementation(Dependencies.Compose.PREVIEW)

    // Navigation
    implementation(Dependencies.Compose.NAVIGATION)

    // Accompanist
    implementation(Dependencies.Accompanist.SYSTEM_UI_CONTROLLER)
    implementation(Dependencies.Accompanist.NAVIGATION_ANIMATION)

    // Hilt
    implementation(Dependencies.HILT)

    // KTX
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.VIEW_MODEL_KTX)

    // Coroutines
    implementation(Dependencies.COROUTINES)

    testImplementation(TestDependencies.JUNIT)
    androidTestImplementation(AndroidTestDependencies.JUNIT)
    androidTestImplementation(AndroidTestDependencies.ESPRESSO)
    debugImplementation(DebugDependencies.COMPOSE_UI_TOOLING)

    debugApi(DebugDependencies.CUSTOM_VIEW)
    debugApi(DebugDependencies.CUSTOM_VIEW_POOLING_CONTAINER)
}