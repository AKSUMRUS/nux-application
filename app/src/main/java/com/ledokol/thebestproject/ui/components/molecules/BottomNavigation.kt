package com.ledokol.thebestproject.ui.components.molecules

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ledokol.thebestproject.ui.navigation.BottomNavItemMain
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigation(navController: NavController,bottomBarState: MutableState<Boolean>) {
    val items = listOf(
        BottomNavItemMain.Profile,
        BottomNavItemMain.QuickGame,
        BottomNavItemMain.Friends,
//        BottomNavItemMain.Notifications
    )
    
    AnimatedVisibility(visible = bottomBarState.value) {

        BottomNavigation(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.Gray,
            modifier = Modifier.height(62.dp),
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
                                .align(CenterVertically)
//                                .padding(bottom = 8.dp)
                                .size(35.dp)
                                .align(CenterVertically)
                            ,
                        )
                    },
//                    label = {
//                        Subtitle2(
//                            text = item.title,
//                            fontWeight = W400,
//                            modifier = Modifier.padding(bottom = 0.dp)
//                            ,
//
//                            color = if(currentRoute == item.screen_route) MaterialTheme.colors.onPrimary else MaterialTheme.colors.secondaryVariant
//                        )
//                    },
                    selectedContentColor = MaterialTheme.colors.onBackground,
                    unselectedContentColor = MaterialTheme.colors.onPrimary,
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
                    modifier = Modifier.align(CenterVertically).padding(bottom = 5.dp),
                )
            }

        }
    }
}