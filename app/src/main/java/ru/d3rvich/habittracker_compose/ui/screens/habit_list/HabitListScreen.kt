package ru.d3rvich.habittracker_compose.ui.screens.habit_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.model.HabitListAction
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.model.HabitListEvent
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.model.HabitListViewState
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.views.HabitListViewContent
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.views.HabitListViewError
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.views.HabitListViewLoading

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun HabitListScreen(
    navController: NavController,
    viewModel: HabitListViewModel,
    openDrawer: () -> Unit
) {
    var isAddHabitButtonVisible by remember {
        mutableStateOf(false)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(modifier = Modifier.fillMaxWidth(), title = {
                Text(text = "My habits")
            }, navigationIcon = {
                IconButton(onClick = openDrawer) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Open drawer")
                }
            })
        },
        floatingActionButton = {
            if (isAddHabitButtonVisible) {
                FloatingActionButton(onClick = { viewModel.obtainEvent(HabitListEvent.OnAddHabitClicked) }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add habit")
                }
            }
        }
    ) {
        when (val viewState = viewModel.uiState.collectAsState().value) {
            is HabitListViewState.Loading -> HabitListViewLoading()
            is HabitListViewState.Content -> {
                isAddHabitButtonVisible = true
                HabitListViewContent(habits = viewState.habits) { habitId ->
                    viewModel.obtainEvent(HabitListEvent.OnHabitClicked(habitId))
                }
            }
            is HabitListViewState.Error -> HabitListViewError {
                viewModel.obtainEvent(HabitListEvent.OnReloadButtonClicked)
            }
        }
    }

    LaunchedEffect(Unit) {
        launch {
            viewModel.uiAction.collect { action ->
                when (action) {
                    HabitListAction.NavigateToHabitCreator -> {
                        navController.navigate("habit_creator")
                    }
                    is HabitListAction.NavigateToHabitEditor -> {
                        navController.navigate("habit_editor/${action.habitId}")
                    }
                }
            }
        }
    }
}

