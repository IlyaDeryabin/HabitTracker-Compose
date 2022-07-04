package dependencies

import DependenciesVersions

/**
 * Created by Ilya Deryabin at 01.07.2022
 *
 * Зависимости для интреграционных тестирования
 */
object AndroidTestDependencies {
    const val JUNIT = "androidx.test.ext:junit:${DependenciesVersions.JUNIT_ANDROID}"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:${DependenciesVersions.ESPRESSO}"
    const val COMPOSE_JUNIT =
        "androidx.compose.ui:ui-test-junit4:${DependenciesVersions.Compose.COMPOSE}"
}