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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.noxis.core.R
import ru.noxis.flagmaster.domain.Сountry
import ru.noxis.flagmaster.ui.theme.FlagMasterTheme

@Composable
fun CountryCard(
    modifier: Modifier = Modifier,
    country: Сountry,
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
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
                painter = painterResource(R.drawable.flag_of_russia),
                contentDescription = null
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = country.name,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "(${country.capitalCity})",
                    fontSize = 16.sp,
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
                        country = Сountry(
                            id = 0L,
                            name = "Россия",
                            capitalCity = "Москва"
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }
        }

    }
}