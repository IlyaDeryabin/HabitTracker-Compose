package ru.d3rvich.habittracker_compose.ui.screens.habit_editor

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.d3rvich.habittracker_compose.data.HabitStore
import ru.d3rvich.habittracker_compose.ui.base.BaseViewModel
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.model.HabitEditorAction
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.model.HabitEditorEvent
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.model.HabitEditorViewState
import javax.inject.Inject

@HiltViewModel
class HabitEditorViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
    BaseViewModel<HabitEditorEvent, HabitEditorViewState, HabitEditorAction>() {

    private var habitId: String? = null

    init {
        habitId = savedStateHandle.get("habitId")
        if (habitId == null) {
            setState(HabitEditorViewState.Creator)
        } else {
            loadHabitBy(habitId!!)
        }
    }

    override fun createInitialState(): HabitEditorViewState = HabitEditorViewState.Loading

    override fun obtainEvent(event: HabitEditorEvent) {
        when (val state = currentState) {
            is HabitEditorViewState.Creator -> reduce(event, state)
            is HabitEditorViewState.Editor -> reduce(event, state)
            is HabitEditorViewState.Error -> reduce(event, state)
            is HabitEditorViewState.Loading -> reduce(event, state)
        }
    }

    private fun loadHabitBy(habitId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val habit = HabitStore.getHabitBy(habitId)
            setState(HabitEditorViewState.Editor(habit))
        }
    }

    private fun reduce(event: HabitEditorEvent, state: HabitEditorViewState.Creator) {
        when (event) {
            HabitEditorEvent.OnBackPressed -> {
                sendAction { HabitEditorAction.PopBackStack }
            }
            is HabitEditorEvent.OnSaveButtonClicked -> {
                viewModelScope.launch {
                    HabitStore.addHabit(event.habit)
                    sendAction { HabitEditorAction.PopBackStack }
                }
            }
            else -> unexpectedEventError(event, state)
        }
    }

    private fun reduce(event: HabitEditorEvent, state: HabitEditorViewState.Editor) {
        when (event) {
            HabitEditorEvent.OnBackPressed -> {
                sendAction { HabitEditorAction.PopBackStack }
            }
            is HabitEditorEvent.OnSaveButtonClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    HabitStore.editHabit(event.habit)
                    sendAction { HabitEditorAction.PopBackStack }
                }
            }
            else -> unexpectedEventError(event, state)
        }
    }

    private fun reduce(event: HabitEditorEvent, state: HabitEditorViewState.Error) {
        when (event) {
            HabitEditorEvent.OnBackPressed -> {
                sendAction { HabitEditorAction.PopBackStack }
            }
            HabitEditorEvent.OnReloadButtonClicked -> {
                loadHabitBy(habitId!!)
            }
            else -> unexpectedEventError(event, state)
        }
    }

    private fun reduce(event: HabitEditorEvent, state: HabitEditorViewState.Loading) {
        unexpectedEventError(event, state)
    }
}