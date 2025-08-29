package ru.noxis.flagmaster.navigation

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.noxis.core.navigation.Route
import ru.noxis.core.navigation.topLevelRoutes

@Composable
fun NavigateRoot() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.Gray
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                topLevelRoutes.forEach { topLevelRoute ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(topLevelRoute.icon),
                                contentDescription = topLevelRoute.name
                            )
                        },
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Green,
                        selected = currentDestination?.hierarchy?.any {
                            it.route?.contains(topLevelRoute.route.toString()) == true
                        } == true,
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
//                                // Pop up to the start destination of the graph to
//                                // avoid building up a large stack of destinations
//                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
//                                // Avoid multiple copies of the same destination when
//                                // reselecting the same item
                                launchSingleTop = true
//                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        },
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Route.ListContent
            ) {
                listScreen(navController, innerPadding)
                cardScreen(navController, innerPadding)
                brainScreen(navController, innerPadding)
            }
        }
    )
}