@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationSpecApi::class)

package ru.noxis.flagmaster.presentation.guide.screen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.noxis.core.domain.CountryInfo
import ru.noxis.core.domain.enum.Сountries
import ru.noxis.core.util.asUiPainter
import ru.noxis.core.util.asUiTextCapitalName
import ru.noxis.core.util.asUiTextCountryName
import ru.noxis.flagmaster.presentation.guide.viewmodel.CountriesViewModel
import ru.noxis.flagmaster.ui.theme.FlagMasterTheme
import ru.noxis.flagmaster.utils.currentPageOffset
import ru.noxis.flagmaster.utils.lerp
import kotlin.math.absoluteValue

@Composable
fun CountriesPagerContent(modifier: Modifier = Modifier) {
    val viewModel: CountriesViewModel = hiltViewModel()
    val countries by viewModel.countries.collectAsState()
    val horizontalPagerState = rememberPagerState(pageCount = { countries.size })
    val verticalPagerState = rememberPagerState(pageCount = { countries.size })

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            state = horizontalPagerState
        ) { currentPage ->

            val pageOffset = horizontalPagerState.currentPageOffset(currentPage)
            val cardAlpha = lerp(0.4f, 1f, 1f - pageOffset.absoluteValue.coerceIn(0f, 1f))
            val cardRotationY = lerp(0f, 40f, pageOffset.coerceIn(-1f, 1f))
            val cardScale = lerp(0.5f, 1f, 1f - pageOffset.absoluteValue.coerceIn(0f, 1f))

            CountryItemView(
                country = countries[currentPage],
                cardScale = cardScale,
                cardAlpha = cardAlpha,
                cardRotationY = cardRotationY,
            )
        }
        Spacer(Modifier.height(32.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            VerticalPager(
                state = verticalPagerState,
                modifier = Modifier.height(72.dp),
                userScrollEnabled = false,
            ) { page ->
                CountryInfoView(countries[page])
            }

            LaunchedEffect(Unit) {
                snapshotFlow {
                    Pair(
                        horizontalPagerState.currentPage,
                        horizontalPagerState.currentPageOffsetFraction
                    )
                }.collect { (page, offset) ->
                    verticalPagerState.scrollToPage(page, offset)
                }
            }
        }
    }
}

@Composable
private fun PagerScope.CountryInfoView(
    country: CountryInfo,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.surface,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = country.nameCountry.asString(),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Thin,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
        )
        Text(
            text = country.capitalCountry.asString(),
            fontSize = 14.sp,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary.copy(alpha = .6f),
            )
        )
    }
}

@Composable
private fun PagerScope.CountryItemView(
    country: CountryInfo,
    cardScale: Float,
    cardAlpha: Float,
    cardRotationY: Float,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    rotationY = cardRotationY
                    alpha = cardAlpha
                    cameraDistance = 8 * density
                    scaleX = cardScale
                    scaleY = cardScale
                }
                .aspectRatio(0.8f),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray)
            )
            Column {
                Image(
                    painter = country.flagIcon.asPainter(),
                    contentDescription = "Shoe Image",
                )
            }
        }

    }
}

@PreviewLightDark
@Composable
private fun CountryInfoViewPreview() {
    FlagMasterTheme {
        val countries = listOf(
            CountryInfo(
                flagIcon = Сountries.CF.asUiPainter(),
                nameCountry = Сountries.CF.asUiTextCountryName(),
                capitalCountry = Сountries.CF.asUiTextCapitalName(),
                code = Сountries.CF.name
            )
        )
        val verticalPagerState = rememberPagerState(pageCount = { countries.size })
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            VerticalPager(
                state = verticalPagerState,
                modifier = Modifier.height(72.dp),
                userScrollEnabled = false,
                horizontalAlignment = Alignment.CenterHorizontally
            ) { page ->
                CountryInfoView(countries[page])
            }
        }
    }
}