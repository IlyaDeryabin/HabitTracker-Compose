package dependencies

import DependenciesVersions

/**
 * Created by Ilya Deryabin at 01.07.2022
 *
 * Debug зависимости проекта
 */
object DebugDependencies {
    const val COMPOSE_UI_TOOLING =
        "androidx.compose.ui:ui-tooling:${DependenciesVersions.Compose.COMPOSE}"
    const val CUSTOM_VIEW =
        "androidx.customview:customview:${DependenciesVersions.Compose.CUSTOM_VIEW}"
    const val CUSTOM_VIEW_POOLING_CONTAINER =
        "androidx.customview:customview-poolingcontainer:${DependenciesVersions.Compose.CUSTOM_VIEW_POOLING_CONTAINER}"
}