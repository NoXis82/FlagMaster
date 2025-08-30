package ru.noxis.core.domain

import android.content.Context
import ru.noxis.core.util.UiPainter
import ru.noxis.core.util.UiText

data class CountryInfo(
    val flagIcon: UiPainter,
    val nameCountry: UiText,
    val capitalCountry: UiText,
    val code: String
) {

    fun doesMatchSearchQuery(query: String, appContext: Context): Boolean {
        val matchingCombinations = listOf(
            nameCountry.asString(appContext),
            capitalCountry.asString(appContext),
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
