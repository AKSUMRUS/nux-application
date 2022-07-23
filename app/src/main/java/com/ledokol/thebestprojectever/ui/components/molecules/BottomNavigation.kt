package com.ledokol.thebestprojectever.ui.components.molecules

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.ui.navigation.BottomNavItemMain
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Subtitle2

@Composable
fun BottomNavigation(navController: NavController,bottomBarState: MutableState<Boolean>) {
    val items = listOf(
        BottomNavItemMain.Profile,
        BottomNavItemMain.QuickGame,
        BottomNavItemMain.Friends
    )
    
    AnimatedVisibility(visible = bottomBarState.value) {

        BottomNavigation(
            backgroundColor = MaterialTheme.colors.onBackground,
            contentColor = Color.Gray,
            modifier = Modifier.height(90.dp),
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = item.title,
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                                .size(40.dp)
                                .align(CenterVertically)
                            ,
                        )
                    },
                    label = {
                        Subtitle2(
                            text = item.title,
                            fontWeight = W700,
                            modifier = Modifier.padding(bottom = 0.dp)
                            ,
                        )
                    },
                    selectedContentColor = MaterialTheme.colors.onPrimary,
                    unselectedContentColor = MaterialTheme.colors.onSecondary,
//                    alwaysShowLabel = true,
                    selected = currentRoute == item.screen_route,
                    onClick = {
                        navController.navigate(item.screen_route) {
                            popUpTo(item.screen_route){
                                inclusive = true
                            }
                            launchSingleTop = true
//                            restoreState = true
                        }
                    },
                    modifier = Modifier.align(CenterVertically).padding(bottom = 8.dp),
                )
            }

        }
    }
}