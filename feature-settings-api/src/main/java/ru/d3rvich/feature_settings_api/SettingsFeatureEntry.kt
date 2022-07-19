package ru.d3rvich.feature_settings_api

import ru.d3rvich.core.feature.AggregateFeatureEntry

/**
 * Created by Ilya Deryabin at 14.07.2022
 *
 * Api фичи настроек
 */
abstract class SettingsFeatureEntry : AggregateFeatureEntry  {
    final override val featureRoute: String
        get() = "settings"

    fun destination() = featureRoute
}