/**
 * Created by Ilya Deryabin at 01.07.2022
 *
 * Конфигурация Android сборки
 */
object BuildAndroidConfig {
    const val APPLICATION_ID = "ru.d3rvich.habittracker_compose"

    const val COMPILE_SDK_VERSION = 33
    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 33

    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"

    const val SUPPORT_LIBRARY_VECTOR_DRAWABLES = true

    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
}