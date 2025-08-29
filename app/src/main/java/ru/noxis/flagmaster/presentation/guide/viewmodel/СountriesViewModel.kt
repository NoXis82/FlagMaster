package ru.noxis.flagmaster.presentation.guide.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.noxis.core.domain.CountryInfo
import ru.noxis.core.domain.enum.Сountries
import ru.noxis.core.util.asUiPainter
import ru.noxis.core.util.asUiTextCapitalName
import ru.noxis.core.util.asUiTextCountryName
import javax.inject.Inject

class СountriesViewModel @Inject constructor() : ViewModel() {

    private val _countries = MutableStateFlow<List<CountryInfo>>(emptyList())
    val countries = _countries.asStateFlow()


    init {
        val countryInfoList = arrayListOf<CountryInfo>()
        Сountries.entries.forEach {
            countryInfoList.add(
                CountryInfo(
                    flagIcon = it.asUiPainter(),
                    nameCountry = it.asUiTextCountryName(),
                    capitalCountry = it.asUiTextCapitalName(),
                    code = it.name
                )
            )

        }
        _countries.value = countryInfoList
    }

}