package ru.d3rvich.habittracker_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.d3rvich.core.feature.AggregateFeatureEntry
import ru.d3rvich.core.feature.ComposableFeatureEntry
import ru.d3rvich.core.feature.Destinations
import ru.d3rvich.core.feature.find
import ru.d3rvich.core.theme.HabitTrackerComposeTheme
import ru.d3rvich.feature_habitlist_api.HabitListFeatureEntry
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var destinations: Destinations

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HabitTrackerComposeTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding(),
                    color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    val startDestination = destinations.find<HabitListFeatureEntry>().destination()
                    NavHost(navController = navController,
                        startDestination = startDestination) {
                        destinations.values.forEach { destination ->
                            with (destination) {
                                when (this) {
                                    is ComposableFeatureEntry -> {
                                        composable(navController, destinations)
                                    }
                                    is AggregateFeatureEntry -> {
                                        navigation(navController, destinations)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}