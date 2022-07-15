package ru.d3rvich.feature_settings_impl

import androidx.navigation.compose.composable
import ru.d3rvich.core.feature.Destinations
import ru.d3rvich.feature_settings_api.SettingsFeatureEntry
import ru.d3rvich.feature_settings_impl.presentation.screens.AboutScreen
import ru.d3rvich.feature_settings_impl.presentation.screens.SettingsScreen
import javax.inject.Inject

/**
 * Created by Ilya Deryabin at 14.07.2022
 *
 * Реализация [SettingsFeatureEntry]
 */
internal class SettingsFeatureEntryImpl @Inject constructor() : SettingsFeatureEntry() {
    override fun androidx.navigation.NavGraphBuilder.navigation(
        navController: androidx.navigation.NavHostController,
        destinations: Destinations,
    ) {
        composable(InternalScreens.SETTINGS.name) {
            SettingsScreen(navigateBack = { navController.popBackStack() }) {
                navController.navigate(InternalScreens.ABOUT.name)
            }
        }
        composable(InternalScreens.ABOUT.name) {
            AboutScreen {
                navController.popBackStack()
            }
        }
    }
}