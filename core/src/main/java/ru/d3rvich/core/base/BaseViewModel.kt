package ru.d3rvich.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Created by Ilya Deryabin at 24.06.2022
 */
abstract class BaseViewModel<Event : UiEvent, State : UiState, Action : UiAction> : ViewModel() {

    private val initialState: State by lazy { createInitialState() }
    protected abstract fun createInitialState(): State

    val currentState: State
        get() = _uiState.value

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _uiAction = Channel<Action>()
    val uiAction = _uiAction.receiveAsFlow()

    protected fun setState(state: State) {
        _uiState.value = state
    }

    protected fun sendAction(block: () -> Action) {
        viewModelScope.launch {
            val action = block()
            _uiAction.send(action)
        }
    }

    abstract fun obtainEvent(event: Event)

    protected fun unexpectedEventError(event: Event, state: State): Nothing =
        error("Unexpected ${event.javaClass.simpleName} event for ${state.javaClass.simpleName} state")
}