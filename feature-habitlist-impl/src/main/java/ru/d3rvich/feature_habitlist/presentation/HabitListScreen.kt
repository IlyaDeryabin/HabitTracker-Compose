package ru.d3rvich.feature_habitlist.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import ru.d3rvich.feature_habitlist.R
import ru.d3rvich.feature_habitlist.presentation.model.HabitListAction
import ru.d3rvich.feature_habitlist.presentation.model.HabitListEvent
import ru.d3rvich.feature_habitlist.presentation.views.HabitListViewContent
import ru.d3rvich.feature_habitlist.presentation.views.HabitListViewFilter
import ru.d3rvich.feature_habitlist.presentation.views.RemoveHabitAlertDialog

/**
 * Created by Ilya Deryabin at 26.06.2022
 */
@OptIn(ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
internal fun HabitListScreen(
    navigateToHabitEditor: (String?) -> Unit,
    navigateToSettings: () -> Unit,
) {
    val viewModel: HabitListViewModel = hiltViewModel()
    val viewState by viewModel.uiState.collectAsStateWithLifecycle()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val sheetPeekHeight = 60.dp
    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = sheetPeekHeight,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        topBar = {
            TopAppBar(modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(text = stringResource(id = R.string.my_habits))
                },
                backgroundColor = MaterialTheme.colors.surface,
                actions = {
                    IconButton(onClick = { navigateToSettings() }) {
                        Icon(imageVector = Icons.Default.Settings,
                            contentDescription = "Navigate to settings")
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.obtainEvent(HabitListEvent.OnAddHabitClicked) }) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_habit))
            }
        },
        sheetContent = {
            HabitListViewFilter(viewModel = viewModel, peekHeight = sheetPeekHeight)
        }
    ) {
        HabitListViewContent(
            habits = viewState.habitList,
            isLoading = viewState.isLoading,
            onHabitClicked = { habitId ->
                viewModel.obtainEvent(HabitListEvent.OnHabitClicked(habitId))
            },
            onHabitLongClicked = { habitId ->
                viewModel.obtainEvent(HabitListEvent.OnHabitLongClicked(habitId))
            })
    }
    RemoveHabitAlertDialog(isVisible = viewState.showDialog, onConfirm = {
        viewModel.obtainEvent(HabitListEvent.OnDeleteDialogResult(true))
    }, onDismiss = {
        viewModel.obtainEvent(HabitListEvent.OnDeleteDialogResult(false))
    })

    val lifecycle = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiAction.collect { action ->
                when (action) {
                    HabitListAction.NavigateToHabitCreator -> {
                        navigateToHabitEditor(null)
                    }
                    is HabitListAction.NavigateToHabitEditor -> {
                        navigateToHabitEditor(action.habitId)
                    }
                }
            }
        }
    }
}