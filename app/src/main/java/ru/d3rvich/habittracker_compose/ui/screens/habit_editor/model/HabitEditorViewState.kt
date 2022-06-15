package ru.d3rvich.habittracker_compose.ui.screens.habit_editor.model

import ru.d3rvich.habittracker_compose.entity.HabitEntity
import ru.d3rvich.habittracker_compose.ui.base.UiState

sealed class HabitEditorViewState : UiState {
    object Loading : HabitEditorViewState()
    data class Creator(val isUploading: Boolean) : HabitEditorViewState()
    data class Editor(val habit: HabitEntity, val isUploading: Boolean) : HabitEditorViewState()
}