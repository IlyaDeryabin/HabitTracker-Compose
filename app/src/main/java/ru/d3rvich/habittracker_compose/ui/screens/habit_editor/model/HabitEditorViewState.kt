package ru.d3rvich.habittracker_compose.ui.screens.habit_editor.model

import androidx.annotation.StringRes
import ru.d3rvich.habittracker_compose.entity.HabitEntity
import ru.d3rvich.habittracker_compose.ui.base.UiState

sealed class HabitEditorViewState : UiState {
    object Loading : HabitEditorViewState()
    object Creator : HabitEditorViewState()
    class Editor(val habit: HabitEntity) : HabitEditorViewState()
    class Error(@StringRes val messageResId: Int) : HabitEditorViewState()
}