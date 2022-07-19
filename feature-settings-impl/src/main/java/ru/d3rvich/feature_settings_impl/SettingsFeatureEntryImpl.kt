package ru.d3rvich.feature_settings_impl

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import ru.d3rvich.core.feature.Destinations
import ru.d3rvich.core.feature.find
import ru.d3rvich.feature_habitlist_api.HabitListFeatureEntry
import ru.d3rvich.feature_settings_api.SettingsFeatureEntry
import ru.d3rvich.feature_settings_impl.presentation.screens.AboutScreen
import ru.d3rvich.feature_settings_impl.presentation.screens.SettingsScreen
import javax.inject.Inject

/**
 * Created by Ilya Deryabin at 14.07.2022
 *
 * Реализация [SettingsFeatureEntry]
 */
@OptIn(ExperimentalAnimationApi::class)
internal class SettingsFeatureEntryImpl @Inject constructor() : SettingsFeatureEntry() {
    override fun NavGraphBuilder.navigation(
        navController: NavHostController,
        destinations: Destinations,
    ) {
        val habitListEntry = destinations.find<HabitListFeatureEntry>()
        composable(InternalScreens.SETTINGS.name,
            enterTransition = {
                when (initialState.destination.route) {
                    habitListEntry.featureRoute -> {
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                    }
                    else -> null
                }
            }, exitTransition = {
                when (targetState.destination.route) {
                    habitListEntry.featureRoute -> {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
                    }
                    else -> null
                }
            }) {
            SettingsScreen(navigateBack = { navController.popBackStack() }) {
                navController.navigate(InternalScreens.ABOUT.name)
            }
        }
        composable(InternalScreens.ABOUT.name,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
            }, exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
            }) {
            AboutScreen {
                navController.popBackStack()
            }
        }
    }
}