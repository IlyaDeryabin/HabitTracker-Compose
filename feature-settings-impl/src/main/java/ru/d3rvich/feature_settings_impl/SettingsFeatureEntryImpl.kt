package ru.d3rvich.feature_settings_impl

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.composable
import ru.d3rvich.core.feature.Destinations
import ru.d3rvich.feature_settings_api.SettingsFeatureEntry
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
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Button(onClick = { navController.navigate(InternalScreens.ABOUT.name) }) {
                    Text(text = "About")
                }
            }
        }
        composable(InternalScreens.ABOUT.name) {
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(text = "ABOUT",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(40.dp))
                Button(onClick = { navController.popBackStack() }) {
                    Text(text = "Back")
                }
            }
        }
    }
}