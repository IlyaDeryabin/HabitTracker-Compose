package ru.d3rvich.feature_habiteditor.presenter.model

import androidx.annotation.StringRes
import ru.d3rvich.core.base.UiAction

internal sealed class HabitEditorAction : UiAction {
    object PopBackStack : HabitEditorAction()
    class ShowMessage(@StringRes val messageResId: Int) : HabitEditorAction()
}