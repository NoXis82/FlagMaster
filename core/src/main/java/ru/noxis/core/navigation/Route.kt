package ru.noxis.core.navigation

import kotlinx.serialization.Serializable
import ru.noxis.core.R
import ru.noxis.core.domain.TopLevelRoute

sealed interface Route {

    @Serializable
    data object Welcome : Route

    @Serializable
    data object ListContent : Route

    @Serializable
    data object CardsContent: Route

    @Serializable
    data object BrainContent: Route
}

val topLevelRoutes = listOf(
    TopLevelRoute("ListContent", Route.ListContent, R.drawable.ic_list_check),
    TopLevelRoute("CardsContent", Route.CardsContent, R.drawable.ic_cards),
    TopLevelRoute("BrainContent", Route.BrainContent, R.drawable.ic_question)
)