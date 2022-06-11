package com.ledokol.thebestprojectever.ui.components.molecules

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.ui.navigation.BottomNavItemMain
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.Subtitle2

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavigation(navController: NavController,bottomBarState: MutableState<Boolean>) {
    val items = listOf(
        BottomNavItemMain.Profile,
        BottomNavItemMain.QuickGame,
        BottomNavItemMain.Friends
    )
    
    AnimatedVisibility(visible = bottomBarState.value) {

        BottomNavigation(
            backgroundColor = colorResource(id = R.color.teal_200),
            contentColor = Color.Black
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = item.title
                        )
                    },
                    label = { Subtitle2(text = item.title) },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Black.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.screen_route,
                    onClick = {
                        navController.navigate(item.screen_route) {
                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

        }
    }
}