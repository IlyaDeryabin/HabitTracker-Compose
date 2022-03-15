package ru.d3rvich.habittracker_compose.ui.screens.habit_editor.model

import ru.d3rvich.habittracker_compose.ui.base.UiAction

sealed class HabitEditorAction : UiAction {
    object PopBackStack : HabitEditorAction()
}