package ru.noxis.core.domain

import ru.noxis.core.util.UiPainter
import ru.noxis.core.util.UiText

data class CountryInfo(
    val flagIcon: UiPainter,
    val nameCountry: UiText,
    val capitalCountry: UiText,
    val code: String
)
