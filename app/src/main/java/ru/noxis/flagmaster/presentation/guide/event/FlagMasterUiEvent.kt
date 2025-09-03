package ru.noxis.flagmaster.presentation.guide.event


sealed interface FlagMasterUiEvent {
    data class OnSearchTextChange(val text: String): FlagMasterUiEvent
    data object StartGame: FlagMasterUiEvent
    data class OnCheckAnswer(val indexAnswer: Int): FlagMasterUiEvent
}