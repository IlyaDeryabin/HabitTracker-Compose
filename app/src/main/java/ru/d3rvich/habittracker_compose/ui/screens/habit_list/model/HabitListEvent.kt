package ru.d3rvich.habittracker_compose.ui.screens.habit_list.model

import ru.d3rvich.habittracker_compose.ui.base.UiEvent

sealed class HabitListEvent : UiEvent {
    object OnAddHabitClicked : HabitListEvent()
    object OnReloadButtonClicked : HabitListEvent()
    class OnHabitClicked(val id: String) : HabitListEvent()
}
