package ru.noxis.core.util

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

sealed class UiPainter {
    class PainterResource(@DrawableRes val resId: Int) : UiPainter()

    @Composable
    fun asPainter(): Painter {
        return when (this) {
            is PainterResource -> painterResource(resId)
        }
    }
}