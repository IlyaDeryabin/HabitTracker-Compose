package ru.d3rvich.feature_habiteditor

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import ru.d3rvich.core.feature.Destinations
import ru.d3rvich.core.feature.NavAnimationSpec
import ru.d3rvich.core.feature.find
import ru.d3rvich.feature_habiteditor.presenter.HabitEditorScreen
import ru.d3rvich.feature_habiteditor_api.HabitEditorFeatureEntry
import ru.d3rvich.feature_habitlist_api.HabitListFeatureEntry
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

    @OptIn(ExperimentalAnimationApi::class)
    override val navAnimationSpec: (NavAnimationSpec.(Destinations) -> Unit)
        get() = { destinations ->
            val habitListEntry = destinations.find<HabitListFeatureEntry>()
            enterTransition = {
                if (initialState.destination.route == habitListEntry.featureRoute) {
                    slideIntoContainer(AnimatedContentScope.SlideDirection.Up)
                } else null
            }
            exitTransition = {
                if (targetState.destination.route == habitListEntry.featureRoute) {
                    slideOutOfContainer(AnimatedContentScope.SlideDirection.Down)
                } else null
            }
        }
}