package ru.noxis.flagmaster.presentation.guide.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.noxis.core.domain.CountryInfo
import ru.noxis.core.domain.enum.Сountries
import ru.noxis.core.util.asUiPainter
import ru.noxis.core.util.asUiTextCapitalName
import ru.noxis.core.util.asUiTextCountryName
import ru.noxis.flagmaster.presentation.guide.components.CountryCard

@Composable
fun CountriesListContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            items(Сountries.entries.size) { index ->
                val countryCode = Сountries.entries[index]
                CountryCard(
                    country = CountryInfo(
                        flagIcon = countryCode.asUiPainter(),
                        nameCountry = countryCode.asUiTextCountryName(),
                        capitalCountry = countryCode.asUiTextCapitalName(),
                        code = countryCode.name
                    )
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}