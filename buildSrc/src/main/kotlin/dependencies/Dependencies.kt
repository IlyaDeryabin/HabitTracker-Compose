package dependencies

import DependenciesVersions

/**
 * Created by Ilya Deryabin at 01.07.2022
 *
 * Всё завивсимости которые есть в проекте
 */
object Dependencies {
    object Compose {
        const val ACTIVITY =
            "androidx.activity:activity-compose:${DependenciesVersions.Compose.ACTIVITY}"
        const val UI = "androidx.compose.ui:ui:${DependenciesVersions.Compose.COMPOSE}"
        const val MATERIAL =
            "androidx.compose.material:material:${DependenciesVersions.Compose.COMPOSE}"
        const val PREVIEW =
            "androidx.compose.ui:ui-tooling-preview:${DependenciesVersions.Compose.COMPOSE}"
        const val NAVIGATION =
            "androidx.navigation:navigation-compose:${DependenciesVersions.Compose.NAVIGATION}"
        const val HILT_NAVIGATION =
            "androidx.hilt:hilt-navigation-compose:${DependenciesVersions.Compose.HILT_NAVIGATION}"
    }

    object Accompanist {
        const val PAGER =
            "com.google.accompanist:accompanist-pager:${DependenciesVersions.Compose.ACCOMPANIST}"
        const val PAGER_INDICATORS =
            "com.google.accompanist:accompanist-pager-indicators:${DependenciesVersions.Compose.ACCOMPANIST}"
        const val SYSTEM_UI_CONTROLLER =
            "com.google.accompanist:accompanist-systemuicontroller:${DependenciesVersions.Compose.ACCOMPANIST}"
        const val NAVIGATION_ANIMATION =
            "com.google.accompanist:accompanist-navigation-animation:${DependenciesVersions.Compose.ACCOMPANIST}"
    }

    object Room {
        const val ROOM = "androidx.room:room-runtime:${DependenciesVersions.ROOM}"
        const val ROOM_KTX = "androidx.room:room-ktx:${DependenciesVersions.ROOM}"
    }

    const val APPCOMPAT = "androidx.appcompat:appcompat:${DependenciesVersions.APPCOMPAT}"
    const val MATERIAL = "com.google.android.material:material:${DependenciesVersions.MATERIAL}"
    const val HILT = "com.google.dagger:hilt-android:${DependenciesVersions.HILT}"
    const val COROUTINES =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${DependenciesVersions.COROUTINES}"
    const val CORE_KTX = "androidx.core:core-ktx:1.9.0"
    const val VIEW_MODEL_KTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${DependenciesVersions.LIFECYCLE_KTX}"
    const val LIFECYCLE_COMPOSE =
        "androidx.lifecycle:lifecycle-runtime-compose:${DependenciesVersions.LIFECYCLE_KTX}"
}