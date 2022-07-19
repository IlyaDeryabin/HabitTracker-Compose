package ru.d3rvich.feature_habitlist

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import ru.d3rvich.core.feature.Destinations
import ru.d3rvich.core.feature.find
import ru.d3rvich.feature_habiteditor_api.HabitEditorFeatureEntry
import ru.d3rvich.feature_habitlist.presentation.HabitListScreen
import ru.d3rvich.feature_habitlist_api.HabitListFeatureEntry
import ru.d3rvich.feature_settings_api.SettingsFeatureEntry
import javax.inject.Inject

/**
 * Created by Ilya Deryabin at 12.07.2022
 */
internal class HabitListFeatureEntryImpl @Inject constructor() : HabitListFeatureEntry() {

    @Composable
    override fun NavGraphBuilder.Composable(
        navController: NavHostController,
        destinations: Destinations,
        backStackEntry: NavBackStackEntry,
    ) {
        val habitEditorEntry = destinations.find<HabitEditorFeatureEntry>()
        HabitListScreen(navigateToHabitEditor = { habitId ->
            if (habitId == null) {
                habitEditorEntry.habitCreatorDestination().also {
                    navController.navigate(it)
                }
            } else {
                habitEditorEntry.habitEditorDestination(habitId = habitId).also {
                    navController.navigate(it)
                }
            }
        }, navigateToSettings = {
            destinations.find<SettingsFeatureEntry>().destination().also {
                navController.navigate(it)
            }
        })
    }
}