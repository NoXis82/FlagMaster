@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationSpecApi::class)

package ru.noxis.flagmaster.presentation.guide.screen

import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.noxis.core.domain.CountryInfo
import ru.noxis.flagmaster.presentation.guide.viewmodel.СountriesViewModel
import ru.noxis.flagmaster.utils.currentPageOffset
import ru.noxis.flagmaster.utils.lerp
import kotlin.math.absoluteValue

@Composable
fun CountriesContent(modifier: Modifier = Modifier) {
    val viewModel: СountriesViewModel = hiltViewModel()
    val countries by viewModel.countries.collectAsState(emptyList())

    val horizontalPagerState = rememberPagerState(pageCount = { countries.size })
    val verticalPagerState = rememberPagerState(pageCount = { countries.size })
    Column(
        modifier = modifier.fillMaxSize(),
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
                currentPage = currentPage
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
                horizontalAlignment = Alignment.CenterHorizontally
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
    country: CountryInfo
) {
    Column {
        Text(
            text = country.nameCountry.asString(),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Thin,
                fontSize = 28.sp,
            )
        )
        Text(
            text = country.capitalCountry.asString(),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black.copy(alpha = .56f),
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
    currentPage: Int
) {

    val boundsTransform = BoundsTransform { initialBounds, targetBounds ->
        keyframes {
            durationMillis = 1000
            initialBounds at 0 using ArcMode.ArcBelow using FastOutSlowInEasing
            targetBounds at 1000
        }
    }

    Box(
        modifier =
        Modifier
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
//                    .sharedElement(
//                        rememberSharedContentState(key = "${ShoesConstants.KEY_BACKGROUND}-$currentPage"),
//                        animatedVisibilityScope = animatedVisibilityScope,
//                        boundsTransform = boundsTransform
//                    )
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