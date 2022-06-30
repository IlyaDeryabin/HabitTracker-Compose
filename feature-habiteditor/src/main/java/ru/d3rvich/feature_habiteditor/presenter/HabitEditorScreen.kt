package ru.d3rvich.feature_habiteditor.presenter

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ru.d3rvich.feature_habiteditor.presenter.model.HabitEditorEvent
import ru.d3rvich.feature_habiteditor.presenter.model.HabitEditorAction
import ru.d3rvich.feature_habiteditor.presenter.model.HabitEditorViewState
import ru.d3rvich.feature_habiteditor.presenter.views.HabitEditorViewEditor
import ru.d3rvich.feature_habiteditor.presenter.views.HabitEditorViewLoading
import ru.d3rvich.feature_habiteditor.R
import ru.d3rvich.feature_habiteditor.deps.HabitEditorComponentViewModel

/**
 * Created by Ilya Deryabin at 29.06.2022
 */
@Composable
fun HabitEditorScreen(navController: NavHostController, habitId: String? = null) {
    val viewModelAssistedFactory = viewModel(HabitEditorComponentViewModel::class.java)
        .habitEditorComponent.habitEditorViewModelAssistedFactory
    val viewModelFactory = remember {
        viewModelAssistedFactory.create(habitId = habitId)
    }
    val viewModel: HabitEditorViewModel = viewModel(factory = viewModelFactory)
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
//                        HabitEditorDepsStore.navRouter.popBack()
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