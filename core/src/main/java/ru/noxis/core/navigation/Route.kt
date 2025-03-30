package ru.noxis.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Welcome : Route
}