package ru.d3rvich.feature_habitlist_api

import ru.d3rvich.core.feature.ComposableFeatureEntry

/**
 * Created by Ilya Deryabin at 12.07.2022
 */
abstract class HabitListFeatureEntry: ComposableFeatureEntry {
    final override val featureRoute: String
        get() = "habit_list"

    fun destination() = featureRoute
}