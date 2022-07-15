package ru.d3rvich.core.feature

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.composable

typealias Destinations = Map<Class<out FeatureEntry>, @JvmSuppressWildcards FeatureEntry>

/**
 * Created by Ilya Deryabin at 12.07.2022
 */
interface FeatureEntry {
    val featureRoute: String

    val arguments: List<NamedNavArgument>
        get() = emptyList()

    val deepLinks: List<NavDeepLink>
        get() = emptyList()
}

interface ComposableFeatureEntry : FeatureEntry {
    fun NavGraphBuilder.composable(navController: NavHostController, destinations: Destinations) {
        composable(featureRoute, arguments, deepLinks) { backStackEntry ->
            Composable(navController = navController,
                destinations = destinations,
                backStackEntry = backStackEntry)
        }
    }

    @Composable
    fun NavGraphBuilder.Composable(
        navController: NavHostController,
        destinations: Destinations,
        backStackEntry: NavBackStackEntry,
    )
}

interface AggregateFeatureEntry : FeatureEntry {
    fun NavGraphBuilder.navigation(navController: NavHostController, destinations: Destinations)
}

inline fun <reified T : FeatureEntry> Destinations.find(): T =
    findOrNull() ?: error("Unable to find '${T::class.java}' destination.")

inline fun <reified T : FeatureEntry> Destinations.findOrNull(): T? =
    this[T::class.java] as? T