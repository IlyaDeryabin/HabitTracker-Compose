package ru.d3rvich.habittracker_compose.ui.screens.habit_list

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.model.HabitListAction
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.model.HabitListEvent
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.model.HabitListViewState
import ru.d3rvich.habittracker_compose.data.HabitStore
import ru.d3rvich.habittracker_compose.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HabitListViewModel @Inject constructor() :
    BaseViewModel<HabitListEvent, HabitListViewState, HabitListAction>() {
    override fun createInitialState(): HabitListViewState = HabitListViewState.Loading

    override fun obtainEvent(event: HabitListEvent) {
        when (val state = currentState) {
            is HabitListViewState.Content -> reduce(event, state)
            is HabitListViewState.Error -> reduce(event, state)
            is HabitListViewState.Loading -> reduce(event, state)
        }
    }

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            setState(HabitListViewState.Loading)
            try {
                HabitStore.getHabits().collect { habits ->
                    setState(HabitListViewState.Content(habits = habits))
                }
            } catch (e: Exception) {
                setState(HabitListViewState.Error(0))
            }
        }
    }

    private fun reduce(event: HabitListEvent, viewState: HabitListViewState.Loading) {
        unexpectedEventError(event, viewState)
    }

    private fun reduce(event: HabitListEvent, viewState: HabitListViewState.Content) {
        when (event) {
            HabitListEvent.OnAddHabitClicked -> {
                sendAction { HabitListAction.NavigateToHabitCreator }
            }
            is HabitListEvent.OnHabitClicked -> {
                sendAction { HabitListAction.NavigateToHabitEditor(habitId = event.id) }
            }
            else -> unexpectedEventError(event, viewState)
        }
    }

    private fun reduce(event: HabitListEvent, viewState: HabitListViewState.Error) {
        when (event) {
            HabitListEvent.OnReloadButtonClicked -> {
                loadData()
            }
            else -> unexpectedEventError(event, viewState)
        }
    }
}