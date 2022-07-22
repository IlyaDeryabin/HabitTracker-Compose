import dependencies.*
import extensions.*

plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.HILT)
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
    implementation(project(BuildModules.CORE))
    implementation(project(BuildModules.API))
    implementation(project(BuildModules.Features.HABIT_EDITOR_API))
    implementation(project(BuildModules.Features.HABIT_LIST_API))

    // Coroutines
    implementation(Dependencies.COROUTINES)

    // Compose
    implementation(Dependencies.Compose.UI)
    implementation(Dependencies.Compose.MATERIAL)
    implementation(Dependencies.Compose.PREVIEW)

    /* Dagger Hilt */
    implementation(Dependencies.HILT)
    kapt(AnnotationProcessorDependencies.HILT)

    // Navigation
    implementation(Dependencies.Compose.NAVIGATION)
    implementation(Dependencies.Compose.HILT_NAVIGATION)

    // Accompanist
    implementation(Dependencies.Accompanist.NAVIGATION_ANIMATION)

    // KTX
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.VIEW_MODEL_KTX)

    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.MATERIAL)

    testImplementation(TestDependencies.JUNIT)
    androidTestImplementation(AndroidTestDependencies.JUNIT)
    androidTestImplementation(AndroidTestDependencies.ESPRESSO)
    debugImplementation(DebugDependencies.COMPOSE_UI_TOOLING)
}