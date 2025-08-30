@file:OptIn(FlowPreview::class, FlowPreview::class)

package ru.noxis.flagmaster.presentation.guide.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.FlowPreview
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
import ru.noxis.core.util.asUiPainter
import ru.noxis.core.util.asUiTextCapitalName
import ru.noxis.core.util.asUiTextCountryName
import javax.inject.Inject

@HiltViewModel
class СountriesViewModel @Inject constructor(
    @ApplicationContext applicationContext: Context
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _countries = MutableStateFlow(initCountries())
    val countries = searchText
        .debounce(500L)
        .combine(_countries) { text, countries ->
            if (text.isBlank()) {
                countries
            } else {
                countries.filter {
                    it.doesMatchSearchQuery(text, applicationContext)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _countries.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
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