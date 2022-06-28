package ru.d3rvich.habittracker_compose.ui.screens.habit_list

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ru.d3rvich.habittracker_compose.data.repositories.HabitRepository
import ru.d3rvich.habittracker_compose.entity.HabitEntity
import ru.d3rvich.habittracker_compose.ui.base.BaseViewModel
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.model.FilterConfig
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.model.HabitListAction
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.model.HabitListEvent
import ru.d3rvich.habittracker_compose.ui.screens.habit_list.model.HabitListViewState
import javax.inject.Inject

class HabitListViewModel @Inject constructor(private val habitRepository: HabitRepository) :
    BaseViewModel<HabitListEvent, HabitListViewState, HabitListAction>() {

    override fun createInitialState(): HabitListViewState =
        HabitListViewState(habitList = null, isLoading = true)

    private val habitsFlow = habitRepository.getHabits()
        .stateIn(scope = CoroutineScope(SupervisorJob(viewModelScope.coroutineContext.job) + Dispatchers.Default),
            started = SharingStarted.WhileSubscribed(),
            initialValue = null)

    private var habitToRemoveId: String? = null

    override fun obtainEvent(event: HabitListEvent) {
        when (event) {
            HabitListEvent.OnAddHabitClicked -> {
                sendAction { HabitListAction.NavigateToHabitCreator }
            }
            is HabitListEvent.OnHabitClicked -> {
                sendAction { HabitListAction.NavigateToHabitEditor(habitId = event.id) }
            }
            is HabitListEvent.OnHabitLongClicked -> {
                habitToRemoveId = event.id
                setState(currentState.copy(showDialog = true))
            }
            is HabitListEvent.OnDeleteDialogResult -> {
                if (event.isConfirmed) {
                    habitToRemoveId?.let {
                        removeHabit(it)
                        habitToRemoveId = null
                    }
                }
                setState(currentState.copy(showDialog = false))
            }
            is HabitListEvent.OnFilterTextChange -> {
                updateViewState(currentState.filterConfig.copy(filterText = event.filterText))
            }
            is HabitListEvent.OnSortDirectionChange -> {
                updateViewState(currentState.filterConfig.copy(sortDirection = event.direction))
            }
            is HabitListEvent.OnSortingMethodChange -> {
                updateViewState(currentState.filterConfig.copy(sortingEngine = event.comparator))
            }
        }
    }

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            setState(currentState.copy(isLoading = true))
            habitsFlow.collect { habits ->
                setState(currentState.copy(habitList = habits, isLoading = false))
            }
        }
    }

    private fun removeHabit(habitId: String) {
        viewModelScope.launch {
            habitsFlow.value?.find { it.id == habitId }?.let { habitToRemove ->
                habitRepository.deleteHabit(habitToRemove)
            }
        }
    }

    private fun updateViewState(
        filterConfig: FilterConfig,
        habits: List<HabitEntity>? = habitsFlow.value,
    ) {
        habits?.let {
            setState(currentState.copy(
                habitList = filterConfig.execute(habits),
                filterConfig = filterConfig))
        }
    }
}