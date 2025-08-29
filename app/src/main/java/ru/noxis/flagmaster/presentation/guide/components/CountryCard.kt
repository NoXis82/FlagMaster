package ru.noxis.flagmaster.presentation.guide.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.noxis.core.domain.CountryInfo
import ru.noxis.core.domain.enum.Сountries
import ru.noxis.core.util.asUiPainter
import ru.noxis.core.util.asUiTextCapitalName
import ru.noxis.core.util.asUiTextCountryName
import ru.noxis.flagmaster.ui.theme.FlagMasterTheme

@Composable
fun CountryCard(
    modifier: Modifier = Modifier,
    country: CountryInfo,
) {
    OutlinedCard(
        border = BorderStroke(1.dp, Color.Black),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val textColor = LocalContentColor.current
            Image(
                modifier = Modifier
                    .size(width = 102.dp, height = 70.dp)
                    .border(1.dp, color = textColor, shape = RectangleShape),
                painter = country.flagIcon.asPainter(),
                contentDescription = null
            )
            Spacer(Modifier.width(4.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = country.nameCountry.asString(),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = country.capitalCountry.asString(),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun CountryCardPreview() {
    FlagMasterTheme {
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            (1..10).forEach {
                item {
                    CountryCard(
                        country = CountryInfo(
                            flagIcon = Сountries.CF.asUiPainter(),
                            nameCountry = Сountries.CF.asUiTextCountryName(),
                            capitalCountry = Сountries.CF.asUiTextCapitalName(),
                            code = Сountries.CF.name
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }
        }

    }
}