package dependencies

import DependenciesVersions

/**
 * Created by Ilya Deryabin at 01.07.2022
 *
 * Зависимости для процессора аннотаций (для kapt, например)
 */
object AnnotationProcessorDependencies {
    const val ROOM = "androidx.room:room-compiler:${DependenciesVersions.ROOM}"
    const val HILT = "com.google.dagger:hilt-compiler:${DependenciesVersions.HILT}"
}