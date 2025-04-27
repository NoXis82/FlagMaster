package ru.noxis.flagmaster.utils

import androidx.compose.foundation.pager.PagerState

/**
 * С помощью currentPageOffset и currentPage мы можем вычислить смещение
 * для любой страницы в нашем пейджере
 */
fun PagerState.currentPageOffset(page: Int) =
    ((currentPage - page) + currentPageOffsetFraction).coerceIn(-1f, 1f)

fun PagerState.pageOffset(page: Int) = (currentPage - page) + currentPageOffsetFraction

/**
 * Function to interpolate between two values with a given amount.
 * */
fun lerp(start: Float, stop: Float, amount: Float): Float {
    return start + (stop - start) * amount
}