package ru.d3rvich.habittracker_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.HabitEditorScreen
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.HabitEditorViewModel
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.HabitListScreen
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.HabitListViewModel
import ru.d3rvich.habittracker_compose.ui.theme.HabitTrackerComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            HabitTrackerComposeTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val scaffoldState = rememberScaffoldState()
                    val coroutineScope = rememberCoroutineScope()
                    BackHandler(enabled = scaffoldState.drawerState.isOpen) {
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        scaffoldState = scaffoldState,
                        drawerContent = { HabitTrackerDrawer() },
                        drawerGesturesEnabled = scaffoldState.drawerState.isOpen
                    ) { paddingValues ->
                        NavHost(navController = navController,
                            startDestination = "habit_list",
                            modifier = Modifier.padding(paddingValues = paddingValues)) {
                            initNavigation(navController = navController) {
                                coroutineScope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HabitTrackerDrawer() {
    Column {
        Text(text = "Item 1")
        Text(text = "Item 2")
    }
}

private fun NavGraphBuilder.initNavigation(navController: NavController, openDrawer: () -> Unit) {
    composable("habit_list") {
        val viewModel: HabitListViewModel = hiltViewModel()
        HabitListScreen(
            navController = navController,
            viewModel = viewModel,
            openDrawer = openDrawer
        )
    }
    composable("habit_editor/{habitId}") {
        val viewModel: HabitEditorViewModel = hiltViewModel()
        HabitEditorScreen(
            navController = navController,
            viewModel = viewModel
        )
    }
    composable("habit_creator") {
        val viewModel: HabitEditorViewModel = hiltViewModel()
        HabitEditorScreen(
            navController = navController,
            viewModel = viewModel
        )
    }
}