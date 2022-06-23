package ru.d3rvich.habittracker_compose.ui.screens.habit_editor

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.d3rvich.habittracker_compose.R
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.model.HabitEditorAction
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.model.HabitEditorEvent
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.model.HabitEditorViewState
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.views.HabitEditorViewEditor
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.views.HabitEditorViewLoading

@Composable
fun HabitEditorScreen(
    navController: NavController,
    viewModel: HabitEditorViewModel,
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(modifier = Modifier.fillMaxWidth(), title = {
            Text(text = stringResource(id = R.string.habit_editor))
        }, navigationIcon = {
            IconButton(onClick = { viewModel.obtainEvent(HabitEditorEvent.OnBackPressed) }) {
                Icon(Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.pop_back))
            }
        }, backgroundColor = MaterialTheme.colors.surface)
    }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (val state = viewModel.uiState.collectAsState().value) {
                is HabitEditorViewState.Creator -> {
                    HabitEditorViewEditor(isUploading = state.isUploading) { baseHabit ->
                        viewModel.obtainEvent(HabitEditorEvent.OnSaveButtonClicked(baseHabit))
                    }
                }
                is HabitEditorViewState.Editor -> {
                    HabitEditorViewEditor(habit = state.habit,
                        isUploading = state.isUploading) { baseHabit ->
                        viewModel.obtainEvent(HabitEditorEvent.OnSaveButtonClicked(baseHabit))
                    }
                }
                HabitEditorViewState.Loading -> {
                    HabitEditorViewLoading()
                }
            }
        }
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        launch {
            viewModel.uiAction.collect { action ->
                when (action) {
                    HabitEditorAction.PopBackStack -> {
                        navController.popBackStack()
                    }
                    is HabitEditorAction.ShowMessage -> {
                        Toast.makeText(context,
                            context.getString(action.messageResId),
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}