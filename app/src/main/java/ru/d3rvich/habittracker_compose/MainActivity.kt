package ru.d3rvich.habittracker_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.d3rvich.core.feature.AggregateFeatureEntry
import ru.d3rvich.core.feature.ComposableFeatureEntry
import ru.d3rvich.core.feature.Destinations
import ru.d3rvich.core.feature.find
import ru.d3rvich.core.theme.HabitTrackerComposeTheme
import ru.d3rvich.feature_habitlist_api.HabitListFeatureEntry
import javax.inject.Inject

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var destinations: Destinations

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            HabitTrackerComposeTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding(),
                    color = MaterialTheme.colors.background) {
                    val navController = rememberAnimatedNavController()
                    val startDestination = destinations.find<HabitListFeatureEntry>().featureRoute
                    AnimatedNavHost(navController = navController,
                        startDestination = startDestination,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        destinations.values.forEach { destination ->
                            with(destination) {
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