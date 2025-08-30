package ru.noxis.flagmaster.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.noxis.core.navigation.Route

fun NavGraphBuilder.brainScreen(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    composable<Route.BrainContent> {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text("BrainContent")
        }
    }
}