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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.noxis.core.domain.CountryInfo
import ru.noxis.flagmaster.presentation.guide.viewmodel.СountriesViewModel
import ru.noxis.flagmaster.utils.currentPageOffset
import ru.noxis.flagmaster.utils.lerp
import kotlin.math.absoluteValue

@Composable
fun CountriesContent() {
    val viewModel: СountriesViewModel = hiltViewModel()
    val countries by viewModel.countries.collectAsState(emptyList())

    val pagerState = rememberPagerState(pageCount = { countries.size })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.padding(vertical = 8.dp)
    ) { currentPage ->

        val pageOffset = pagerState.currentPageOffset(currentPage)
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
            modifier =
            Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    rotationY = cardRotationY
                    alpha = cardAlpha
                    cameraDistance = 8 * density
                    scaleX = cardScale
                    scaleY = cardScale
                }
                .aspectRatio(0.8f)
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
                Text(text = country.nameCountry.asString())
                Image(
                    painter = country.flagIcon.asPainter(),
                    contentDescription = "Shoe Image",
                )
            }
        }

    }
}