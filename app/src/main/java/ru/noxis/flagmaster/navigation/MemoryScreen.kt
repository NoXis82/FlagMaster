package ru.noxis.flagmaster.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.noxis.core.navigation.Route
import ru.noxis.flagmaster.presentation.guide.screen.MemoryContent

fun NavGraphBuilder.memoryScreen(innerPadding: PaddingValues) {
    composable<Route.MemoryContent> {
        MemoryContent(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding))
    }
}