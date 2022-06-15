package ru.d3rvich.habittracker_compose.ui.screens.habit_editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.d3rvich.habittracker_compose.R
import ru.d3rvich.habittracker_compose.data.repositories.HabitRepository
import ru.d3rvich.habittracker_compose.entity.BaseHabitEntity
import ru.d3rvich.habittracker_compose.entity.toHabitEntity
import ru.d3rvich.habittracker_compose.ui.base.BaseViewModel
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.model.HabitEditorAction
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.model.HabitEditorEvent
import ru.d3rvich.habittracker_compose.ui.screens.habit_editor.model.HabitEditorViewState
import javax.inject.Inject

@HiltViewModel
class HabitEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val habitRepository: HabitRepository,
) : BaseViewModel<HabitEditorEvent, HabitEditorViewState, HabitEditorAction>() {

    private var habitId: String? = null

    init {
        habitId = savedStateHandle.get("habitId")
        habitId?.let {
            loadHabitBy(it)
        } ?: setState(HabitEditorViewState.Creator(false))
    }

    override fun createInitialState(): HabitEditorViewState = HabitEditorViewState.Loading

    override fun obtainEvent(event: HabitEditorEvent) {
        when (val state = currentState) {
            is HabitEditorViewState.Creator -> reduce(event, state)
            is HabitEditorViewState.Editor -> reduce(event, state)
            is HabitEditorViewState.Loading -> reduce(event, state)
        }
    }

    private fun loadHabitBy(habitId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val habit = habitRepository.getHabitBy(habitId)
            setState(HabitEditorViewState.Editor(habit, false))
        }
    }

    private fun checkFields(habit: BaseHabitEntity): Boolean {
        if (habit.color == null || habit.count == null || habit.frequency == null || habit.type == null) return false
        if (habit.title.isEmpty() || habit.description.isEmpty()) return false
        return true
    }

    private fun reduce(event: HabitEditorEvent, state: HabitEditorViewState.Creator) {
        when (event) {
            HabitEditorEvent.OnBackPressed -> {
                sendAction { HabitEditorAction.PopBackStack }
            }
            is HabitEditorEvent.OnSaveButtonClicked -> {
                viewModelScope.launch {
                    setState(state.copy(isUploading = true))
                    if (checkFields(event.habit)) {
                        habitRepository.createHabit(event.habit.toHabitEntity())
                        sendAction { HabitEditorAction.PopBackStack }
                    } else {
                        setState(state.copy(isUploading = false))
                        sendAction { HabitEditorAction.ShowMessage(R.string.fill_all_fields) }
                    }
                }
            }
        }
    }

    private fun reduce(event: HabitEditorEvent, state: HabitEditorViewState.Editor) {
        when (event) {
            HabitEditorEvent.OnBackPressed -> {
                sendAction { HabitEditorAction.PopBackStack }
            }
            is HabitEditorEvent.OnSaveButtonClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    setState(state.copy(isUploading = true))
                    if (checkFields(event.habit)) {
                        habitRepository.updateHabit(event.habit.toHabitEntity(id = state.habit.id,
                            date = state.habit.date,
                            doneDates = state.habit.doneDates))
                        sendAction { HabitEditorAction.PopBackStack }
                    } else {
                        setState(state.copy(isUploading = false))
                        sendAction { HabitEditorAction.ShowMessage(R.string.fill_all_fields) }
                    }
                }
            }
        }
    }

    private fun reduce(event: HabitEditorEvent, state: HabitEditorViewState.Loading) {
        unexpectedEventError(event, state)
    }
}