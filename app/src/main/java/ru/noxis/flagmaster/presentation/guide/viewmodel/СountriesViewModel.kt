@file:OptIn(FlowPreview::class)

package ru.noxis.flagmaster.presentation.guide.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import ru.noxis.core.domain.CountryInfo
import ru.noxis.core.domain.enum.Сountries
import ru.noxis.core.presentation.viewmodel.StateAndEventViewModel
import ru.noxis.core.util.asUiPainter
import ru.noxis.core.util.asUiTextCapitalName
import ru.noxis.core.util.asUiTextCountryName
import ru.noxis.flagmaster.presentation.guide.event.FlagMasterUiEvent
import ru.noxis.flagmaster.presentation.guide.state.FlagMasterUiState
import ru.noxis.flagmaster.presentation.guide.state.TypeAnswer
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject internal constructor(
    @ApplicationContext applicationContext: Context
) : StateAndEventViewModel<FlagMasterUiState, FlagMasterUiEvent>(FlagMasterUiState()) {

    private val _searchText = MutableStateFlow("")
    private val searchText = _searchText.asStateFlow()

    private val _countries = MutableStateFlow(initCountries())
    val countries = searchText
        .debounce(500L)
        .onEach { updateUIState { copy(isSearching = true) } }
        .combine(_countries) { text, countries ->
            if (text.isBlank()) {
                countries
            } else {
                delay(1000L)
                countries.filter {
                    it.doesMatchSearchQuery(text, applicationContext)
                }
            }
        }
        .onEach { updateUIState { copy(isSearching = false) } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _countries.value
        )

    override suspend fun handleEvent(uiEvent: FlagMasterUiEvent) {
        when (uiEvent) {
            is FlagMasterUiEvent.OnSearchTextChange -> {
                onSearchTextChange(uiEvent.text)
            }

            FlagMasterUiEvent.StartGame -> {
                val randomElements = initCountries().shuffled().take(3)
                val randomIndex = (0..2).random()
                updateUIState {
                    copy(
                        randomElements = randomElements,
                        randomIndex = randomIndex,
                        answerCheck = TypeAnswer.NotAnswer
                    )
                }
            }

            is FlagMasterUiEvent.OnCheckAnswer -> {
                updateUIState {
                    copy(
                        answerCheck = TypeAnswer.OnAnswer,
                        answerIndex = uiEvent.indexAnswer
                    )
                }
            }
        }
    }

    private fun onSearchTextChange(text: String) {
        updateUIState {
            copy(searchText = text)
        }
        _searchText.update { text }
    }

    private fun initCountries(): List<CountryInfo> {
        val countries = arrayListOf<CountryInfo>()
        Сountries.entries.forEach {
            countries.add(
                CountryInfo(
                    flagIcon = it.asUiPainter(),
                    nameCountry = it.asUiTextCountryName(),
                    capitalCountry = it.asUiTextCapitalName(),
                    code = it.name
                )
            )

        }
        return countries
    }

}