package ru.noxis.flagmaster.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.noxis.core.navigation.Route
import ru.noxis.flagmaster.presentation.guide.screen.CountriesPagerContent

fun NavGraphBuilder.cardScreen(innerPadding: PaddingValues) {
    composable<Route.CardsContent> {
        CountriesPagerContent(modifier = Modifier.padding(innerPadding))
    }
}