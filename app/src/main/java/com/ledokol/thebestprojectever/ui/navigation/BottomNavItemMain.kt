package com.ledokol.thebestprojectever.ui.navigation

import com.ledokol.thebestprojectever.R

// Какие экраны в навигации должны быть, класс принимает одно из значений
sealed class BottomNavItemMain(var title: String, var icon: Int, var screen_route: String) {

    object QuickGame : BottomNavItemMain(
        "Quick game",
        R.drawable.ic_launcher_foreground,
        "quick_game"
    )

    object Friends : BottomNavItemMain(
        "Friends",
        R.drawable.ic_launcher_foreground,
        "friends"
    )

    object Profile : BottomNavItemMain(
        "Profile",
        R.drawable.ic_launcher_foreground,
        "profile"
    )
}