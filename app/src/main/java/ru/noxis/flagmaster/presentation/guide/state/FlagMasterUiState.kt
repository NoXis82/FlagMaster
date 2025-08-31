package ru.noxis.flagmaster.presentation.guide.state

import androidx.compose.runtime.Stable
import ru.noxis.core.domain.CountryInfo
import ru.noxis.core.util.UiText

@Stable
data class FlagMasterUiState(
    val isSearching: Boolean = false,
    val searchText: String = "",
    val randomElements: List<CountryInfo> = emptyList(),
    val randomIndex: Int = 0,
    val answerIndex: Int = 0,
    val answerCheck: TypeAnswer = TypeAnswer.NotAnswer
)

sealed interface TypeAnswer {
    data object NotAnswer: TypeAnswer
    data object OnAnswer: TypeAnswer
}