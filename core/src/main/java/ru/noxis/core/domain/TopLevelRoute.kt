package ru.noxis.core.domain

import androidx.annotation.DrawableRes

data class TopLevelRoute<T : Any>(
    val name: String,
    val route: T,
    @DrawableRes val icon: Int
)
