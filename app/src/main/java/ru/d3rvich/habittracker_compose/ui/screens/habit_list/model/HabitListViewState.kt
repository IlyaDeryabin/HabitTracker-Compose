package ru.d3rvich.habittracker_compose.ui.screens.habit_list.model

import ru.d3rvich.habittracker_compose.entity.HabitEntity
import ru.d3rvich.habittracker_compose.ui.base.UiState

sealed class HabitListViewState : UiState {
    object Loading : HabitListViewState()
    data class Content(val habits: List<HabitEntity>) : HabitListViewState()
    class Error(val messageResId: Int) : HabitListViewState()
}
