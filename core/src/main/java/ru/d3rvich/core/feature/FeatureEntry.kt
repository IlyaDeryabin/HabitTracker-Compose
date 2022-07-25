package ru.d3rvich.core.feature

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable

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
    val navAnimationSpec: (NavAnimationSpec.(Destinations) -> Unit)?
        get() = null

    fun NavGraphBuilder.composable(navController: NavHostController, destinations: Destinations) {
        val animationSpec: NavAnimationSpec? = navAnimationSpec?.let {
            NavAnimationSpec().apply { it(destinations) }
        }
        @OptIn(ExperimentalAnimationApi::class)
        composable(route = featureRoute,
            arguments = arguments,
            deepLinks = deepLinks,
            enterTransition = animationSpec?.enterTransition,
            exitTransition = animationSpec?.exitTransition,
            popEnterTransition = animationSpec?.popEnterTransition,
            popExitTransition = animationSpec?.popExitTransition
        ) { backStackEntry ->
            Composable(navController = navController,
                destinations = destinations,
                backStackEntry = backStackEntry)
        }
    }

    @Composable
    fun Composable(
        navController: NavHostController,
        destinations: Destinations,
        backStackEntry: NavBackStackEntry,
    )
}

@OptIn(ExperimentalAnimationApi::class)
class NavAnimationSpec {
    var enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null
        set(value) {
            field = value
            if (popEnterTransition == null) {
                popEnterTransition = value
            }
        }

    var exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null
        set(value) {
            field = value
            if (popExitTransition == null) {
                popExitTransition = value
            }
        }

    var popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null

    var popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null
}

interface AggregateFeatureEntry : FeatureEntry {
    fun NavGraphBuilder.navigation(navController: NavHostController, destinations: Destinations)
}

inline fun <reified T : FeatureEntry> Destinations.find(): T =
    findOrNull() ?: error("Unable to find '${T::class.java}' destination.")

inline fun <reified T : FeatureEntry> Destinations.findOrNull(): T? =
    this[T::class.java] as? T