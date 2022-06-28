package ru.d3rvich.feature_habitlist.presentation.model

import ru.d3rvich.core.base.UiEvent

/**
 * Created by Ilya Deryabin at 24.06.2022
 */
internal sealed class HabitListEvent : UiEvent {
    object OnAddHabitClicked : HabitListEvent()
    class OnHabitClicked(val id: String) : HabitListEvent()
    class OnHabitLongClicked(val id: String): HabitListEvent()
    class OnDeleteDialogResult(val isConfirmed: Boolean): HabitListEvent()
    class OnFilterTextChange(val filterText: String) : HabitListEvent()
    class OnSortingMethodChange(val comparator: HabitSortingEngine) : HabitListEvent()
    class OnSortDirectionChange(val direction: SortDirection) : HabitListEvent()
}