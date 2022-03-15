package ru.d3rvich.habittracker_compose.ui.screens.habit_editor.model

import ru.d3rvich.habittracker_compose.entity.HabitEntity
import ru.d3rvich.habittracker_compose.ui.base.UiEvent

sealed class HabitEditorEvent : UiEvent {
    object OnBackPressed : HabitEditorEvent()
    object OnReloadButtonClicked : HabitEditorEvent()
    class OnSaveButtonClicked(val habit: HabitEntity) : HabitEditorEvent()
}