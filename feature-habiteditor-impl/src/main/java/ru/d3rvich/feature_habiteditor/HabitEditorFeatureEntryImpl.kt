package ru.d3rvich.feature_habiteditor

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import ru.d3rvich.core.feature.Destinations
import ru.d3rvich.feature_habiteditor.presenter.HabitEditorScreen
import ru.d3rvich.feature_habiteditor_api.HabitEditorFeatureEntry
import javax.inject.Inject

/**
 * Created by Ilya Deryabin at 12.07.2022
 */
internal class HabitEditorFeatureEntryImpl @Inject constructor() : HabitEditorFeatureEntry() {

    @Composable
    override fun NavGraphBuilder.Composable(
        navController: NavHostController,
        destinations: Destinations,
        backStackEntry: NavBackStackEntry,
    ) {
        HabitEditorScreen {
            navController.popBackStack()
        }
    }
}