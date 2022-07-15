package ru.d3rvich.feature_habiteditor_api

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import ru.d3rvich.core.feature.ComposableFeatureEntry

/**
 * Created by Ilya Deryabin at 12.07.2022
 */
abstract class HabitEditorFeatureEntry : ComposableFeatureEntry {
    private val baseFeatureRoute = "habit_editor"
    final override val featureRoute: String
        get() = "$baseFeatureRoute?$HABIT_ID_ARG={$HABIT_ID_ARG}"

    final override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(HABIT_ID_ARG) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )

    fun habitCreatorDestination(): String = baseFeatureRoute

    fun habitEditorDestination(habitId: String): String = "$baseFeatureRoute?$HABIT_ID_ARG=$habitId"

    protected companion object {
        const val HABIT_ID_ARG = "habitId"
    }
}