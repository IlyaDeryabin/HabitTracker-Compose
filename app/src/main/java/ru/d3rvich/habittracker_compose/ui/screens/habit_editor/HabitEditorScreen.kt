package ru.d3rvich.habittracker_compose.ui.screens.habit_editor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.model.HabitEditorAction
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.model.HabitEditorEvent
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.model.HabitEditorViewState
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.views.HabitEditorViewEditor
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.views.HabitEditorViewError
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.views.HabitEditorViewLoading

@Composable
fun HabitEditorScreen(
    navController: NavController,
    viewModel: HabitEditorViewModel
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(modifier = Modifier.fillMaxWidth(), title = {
            Text(text = "Habit editor")
        }, navigationIcon = {
            IconButton(onClick = { viewModel.obtainEvent(HabitEditorEvent.OnBackPressed) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Pop back")
            }
        })
    }) {
        when (val state = viewModel.uiState.collectAsState().value) {
            HabitEditorViewState.Creator -> {
                HabitEditorViewEditor { habit ->
                    viewModel.obtainEvent(HabitEditorEvent.OnSaveButtonClicked(habit))
                }
            }
            is HabitEditorViewState.Editor -> {
                HabitEditorViewEditor(habit = state.habit) { habit ->
                    viewModel.obtainEvent(HabitEditorEvent.OnSaveButtonClicked(habit))
                }
            }
            is HabitEditorViewState.Error -> {
                HabitEditorViewError(errorTextResId = state.messageResId) {
                    viewModel.obtainEvent(HabitEditorEvent.OnReloadButtonClicked)
                }
            }
            HabitEditorViewState.Loading -> {
                HabitEditorViewLoading()
            }
        }
    }

    LaunchedEffect(Unit) {
        launch {
            viewModel.uiAction.collect { action ->
                when (action) {
                    HabitEditorAction.PopBackStack -> {
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}