package ru.d3rvich.habittracker_compose.ui.screens.habit_list.model

import ru.d3rvich.habittracker_compose.ui.base.UiAction

sealed class HabitListAction : UiAction {
    object NavigateToHabitCreator : HabitListAction()
    class NavigateToHabitEditor(val habitId: String) : HabitListAction()
}