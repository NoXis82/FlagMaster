package ru.noxis.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class StateAndEventViewModel<UiState, UiEvent>(initialState: UiState) :
    ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            _uiEvent.receiveAsFlow().collect { uiEvent ->
                handleEvent(uiEvent)
            }
        }
    }

    protected abstract suspend fun handleEvent(uiEvent: UiEvent)

    protected fun updateUIState(updateUIState: UiState.() -> UiState) {
        _uiState.update { _uiState.value.updateUIState() }
    }

    fun onUIEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}
