package ru.d3rvich.habittracker_compose.ui.screens.habit_list.model

import ru.d3rvich.habittracker_compose.ui.base.UiEvent

sealed class HabitListEvent : UiEvent {
    object OnAddHabitClicked : HabitListEvent()
    class OnHabitClicked(val id: String) : HabitListEvent()
    class OnHabitLongClicked(val id: String): HabitListEvent()
    class OnDeleteDialogResult(val isConfirmed: Boolean): HabitListEvent()
}
