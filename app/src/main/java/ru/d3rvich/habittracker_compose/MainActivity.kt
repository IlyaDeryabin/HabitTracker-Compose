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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.d3rvich.core.theme.HabitTrackerComposeTheme
import ru.d3rvich.feature_habiteditor.deps.HabitEditorDepsStore
import ru.d3rvich.feature_habiteditor.deps.HabitEditorNavRouter
import ru.d3rvich.feature_habiteditor.presenter.HabitEditorScreen
import ru.d3rvich.feature_habitlist.deps.HabitListDepsStore
import ru.d3rvich.feature_habitlist.deps.HabitListNavRouter
import ru.d3rvich.feature_habitlist.presentation.HabitListScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provideRouters()
        setContent {
            HabitTrackerComposeTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding(),
                    color = MaterialTheme.colors.background) {
                    navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "habit_list") {
                        composable("habit_list") {
                            HabitListScreen(navController = navController)
                        }
                        composable("habit_editor/{habitId}") { backStackEntry ->
                            val habitId = backStackEntry.arguments?.getString("habitId", null)
                                ?: error("Missing argument habitId")
                            HabitEditorScreen(navController = navController, habitId = habitId)
                        }
                        composable("habit_creator") {
                            HabitEditorScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }

    private fun provideRouters() {
        HabitListDepsStore.navRouter = HabitListNavRouterImpl()
        HabitEditorDepsStore.navRouter = HabitEditorNavRouterImpl()
    }

    inner class HabitListNavRouterImpl : HabitListNavRouter {
        override fun navigateToHabitCreator() {
            navController.navigate("habit_creator")
        }

        override fun navigateToHabitEditorBy(id: String) {
            navController.navigate("habit_editor/$id")
        }
    }

    inner class HabitEditorNavRouterImpl : HabitEditorNavRouter {
        override fun popBack() {
            navController.popBackStack()
        }
    }
}