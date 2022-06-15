package ru.d3rvich.habittracker_compose.ui.screens.habit_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch
import ru.d3rvich.habittracker_compose.R
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.model.HabitListAction
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.model.HabitListEvent
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.views.HabitListViewContent

@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun HabitListScreen(
    navController: NavController,
    viewModel: HabitListViewModel,
    openDrawer: () -> Unit,
) {
    val viewState = viewModel.uiState.collectAsState().value
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
            FloatingActionButton(onClick = { viewModel.obtainEvent(HabitListEvent.OnAddHabitClicked) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add habit")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            HabitListViewContent(habits = viewState.habitList,
                isLoading = viewState.isLoading,
                onHabitClicked = { habitId ->
                    viewModel.obtainEvent(HabitListEvent.OnHabitClicked(habitId))
                },
                onHabitLongClicked = { habitId ->
                    viewModel.obtainEvent(HabitListEvent.OnHabitLongClicked(habitId))
                })
        }
    }
    if (viewState.showDialog) {
        AlertDialog(
            onDismissRequest = {
                viewModel.obtainEvent(HabitListEvent.OnDeleteDialogResult(false))
            },
            title = {
                Text(text = stringResource(id = R.string.delete_habit))
            },
            text = {
                Text(text = stringResource(id = R.string.remove_message))
            },
            confirmButton = {
                TextButton(onClick = { viewModel.obtainEvent(HabitListEvent.OnDeleteDialogResult(true)) }) {
                    Text(stringResource(id = R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.obtainEvent(HabitListEvent.OnDeleteDialogResult(false)) }) {
                    Text(stringResource(id = R.string.cancel))
                }
            })
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