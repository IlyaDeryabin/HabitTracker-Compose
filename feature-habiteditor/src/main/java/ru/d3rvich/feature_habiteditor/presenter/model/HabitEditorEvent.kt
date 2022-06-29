package ru.d3rvich.feature_habiteditor.presenter.model

import ru.d3rvich.core.base.UiEvent
import ru.d3rvich.feature_habiteditor.domain.entities.BaseHabitEntity

internal sealed class HabitEditorEvent : UiEvent {
    object OnBackPressed : HabitEditorEvent()
    class OnSaveButtonClicked(val habit: BaseHabitEntity) : HabitEditorEvent()
}