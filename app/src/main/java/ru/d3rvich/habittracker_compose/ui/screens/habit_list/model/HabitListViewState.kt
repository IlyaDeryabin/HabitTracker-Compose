package ru.d3rvich.habittracker_compose.ui.screens.habit_list.model

import ru.d3rvich.habittracker_compose.entity.HabitEntity
import ru.d3rvich.habittracker_compose.ui.base.UiState

data class HabitListViewState(
    val habitList: List<HabitEntity>?,
    val isLoading: Boolean = false,
    val showDialog: Boolean = false,
    val filterConfig: FilterConfig = FilterConfig.Empty,
) : UiState
