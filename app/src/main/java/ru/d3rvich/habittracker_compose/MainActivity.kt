package ru.d3rvich.habittracker_compose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.d3rvich.feature_habitlist.deps.HabitListDepsStore
import ru.d3rvich.feature_habitlist.deps.HabitListNavRouter
import ru.d3rvich.feature_habitlist.presentation.HabitListFragment
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.HabitEditorScreen
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.HabitEditorViewModel
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.HabitListScreen
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.HabitListViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        provideRouters()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, HabitListFragment())
            .commit()
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        setContent {
//            HabitTrackerComposeTheme {
//                Surface(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .statusBarsPadding()
//                        .navigationBarsPadding(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    val navController = rememberNavController()
//                    val scaffoldState = rememberScaffoldState()
//                    val coroutineScope = rememberCoroutineScope()
//                    BackHandler(enabled = scaffoldState.drawerState.isOpen) {
//                        coroutineScope.launch {
//                            scaffoldState.drawerState.close()
//                        }
//                    }
//                    Scaffold(
//                        modifier = Modifier.fillMaxSize(),
//                        scaffoldState = scaffoldState,
//                        drawerContent = { HabitTrackerDrawer() },
//                        drawerGesturesEnabled = scaffoldState.drawerState.isOpen
//                    ) { paddingValues ->
//                        NavHost(navController = navController,
//                            startDestination = "habit_list",
//                            modifier = Modifier.padding(paddingValues = paddingValues)) {
//                            initNavigation(navController = navController) {
//                                coroutineScope.launch {
//                                    scaffoldState.drawerState.open()
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    private fun provideRouters() {
        HabitListDepsStore.navRouter = HabitListNavRouterImpl()
    }

    inner class HabitListNavRouterImpl : HabitListNavRouter {
        override fun navigateToHabitCreator() {
            Toast.makeText(this@MainActivity, "navigateToHabitCreator", Toast.LENGTH_SHORT)
                .show()
        }

        override fun navigateToHabitEditorBy(id: String) {
            Toast.makeText(this@MainActivity, "navigateToHabitEditorBy $id", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun NavGraphBuilder.initNavigation(
        navController: NavController,
        openDrawer: () -> Unit,
    ) {
        composable("habit_list") {
            val viewModel: HabitListViewModel by viewModels()
            HabitListScreen(
                navController = navController,
                viewModel = viewModel,
                openDrawer = openDrawer
            )
        }
        composable("habit_editor/{habitId}") {
            val viewModel: HabitEditorViewModel by viewModels()
            HabitEditorScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable("habit_creator") {
            val viewModel: HabitEditorViewModel by viewModels()
            HabitEditorScreen(
                navController = navController,
                viewModel = viewModel
            )
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