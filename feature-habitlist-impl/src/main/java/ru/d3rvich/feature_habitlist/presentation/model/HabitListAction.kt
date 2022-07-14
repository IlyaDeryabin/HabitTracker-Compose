package ru.d3rvich.feature_habitlist.presentation.model

import ru.d3rvich.core.base.UiAction

/**
 * Created by Ilya Deryabin at 24.06.2022
 */
internal sealed class HabitListAction : UiAction {
    object NavigateToHabitCreator : HabitListAction()
    class NavigateToHabitEditor(val habitId: String) : HabitListAction()
}