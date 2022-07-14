package ru.d3rvich.feature_habiteditor.presenter.model

import ru.d3rvich.core.base.UiState
import ru.d3rvich.feature_habiteditor.domain.entities.HabitEntity

internal sealed class HabitEditorViewState : UiState {
    object Loading : HabitEditorViewState()
    data class Creator(val isUploading: Boolean) : HabitEditorViewState()
    data class Editor(val habit: HabitEntity, val isUploading: Boolean) : HabitEditorViewState()
}