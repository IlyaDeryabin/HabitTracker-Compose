package ru.d3rvich.feature_habiteditor.presenter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.d3rvich.core.base.BaseViewModel
import ru.d3rvich.feature_habiteditor.R
import ru.d3rvich.feature_habiteditor.domain.entities.BaseHabitEntity
import ru.d3rvich.feature_habiteditor.domain.entities.toHabitEntity
import ru.d3rvich.feature_habiteditor.domain.usecases.CreateHabitUseCase
import ru.d3rvich.feature_habiteditor.domain.usecases.GetHabitUseCase
import ru.d3rvich.feature_habiteditor.domain.usecases.UpdateHabitUseCase
import ru.d3rvich.feature_habiteditor.presenter.model.HabitEditorAction
import ru.d3rvich.feature_habiteditor.presenter.model.HabitEditorEvent
import ru.d3rvich.feature_habiteditor.presenter.model.HabitEditorViewState
import javax.inject.Inject

/**
 * Created by Ilya Deryabin at 28.06.2022
 */
@HiltViewModel
internal class HabitEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getHabitUseCase: GetHabitUseCase,
    private val createHabitUseCase: CreateHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
) : BaseViewModel<HabitEditorEvent, HabitEditorViewState, HabitEditorAction>() {

    companion object {
        const val HABIT_ID = "habitId"
    }

    init {
        val habitId: String? = savedStateHandle.get(HABIT_ID)
        habitId?.let {
            loadHabitBy(it)
        } ?: setState(HabitEditorViewState.Creator(false))
    }

    override fun createInitialState(): HabitEditorViewState = HabitEditorViewState.Loading

    override fun obtainEvent(event: HabitEditorEvent) {
        when (val state = currentState) {
            is HabitEditorViewState.Creator -> reduce(event, state)
            is HabitEditorViewState.Editor -> reduce(event, state)
            is HabitEditorViewState.Loading -> reduce(event, state)
        }
    }

    private fun loadHabitBy(habitId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val habit = getHabitUseCase(habitId)
            setState(HabitEditorViewState.Editor(habit, false))
        }
    }

    private fun checkFields(habit: BaseHabitEntity): Boolean {
        if (habit.color == null || habit.count == null || habit.frequency == null || habit.type == null) return false
        if (habit.title.isEmpty() || habit.description.isEmpty()) return false
        return true
    }

    private fun reduce(event: HabitEditorEvent, state: HabitEditorViewState.Creator) {
        when (event) {
            HabitEditorEvent.OnBackPressed -> {
                sendAction { HabitEditorAction.PopBackStack }
            }
            is HabitEditorEvent.OnSaveButtonClicked -> {
                viewModelScope.launch {
                    setState(state.copy(isUploading = true))
                    if (checkFields(event.habit)) {
                        createHabitUseCase(event.habit.toHabitEntity())
                        sendAction { HabitEditorAction.PopBackStack }
                    } else {
                        setState(state.copy(isUploading = false))
                        sendAction { HabitEditorAction.ShowMessage(R.string.fill_all_fields) }
                    }
                }
            }
        }
    }

    private fun reduce(event: HabitEditorEvent, state: HabitEditorViewState.Editor) {
        when (event) {
            HabitEditorEvent.OnBackPressed -> {
                sendAction { HabitEditorAction.PopBackStack }
            }
            is HabitEditorEvent.OnSaveButtonClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    setState(state.copy(isUploading = true))
                    if (checkFields(event.habit)) {
                        updateHabitUseCase(event.habit.toHabitEntity(id = state.habit.id,
                            date = state.habit.date,
                            doneDates = state.habit.doneDates))
                        sendAction { HabitEditorAction.PopBackStack }
                    } else {
                        setState(state.copy(isUploading = false))
                        sendAction { HabitEditorAction.ShowMessage(R.string.fill_all_fields) }
                    }
                }
            }
        }
    }

    private fun reduce(event: HabitEditorEvent, state: HabitEditorViewState.Loading) {
        unexpectedEventError(event, state)
    }
}