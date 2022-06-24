package com.ledokol.thebestprojectever.ui.navigation

import com.ledokol.thebestprojectever.R

// Какие экраны в навигации должны быть, класс принимает одно из значений
sealed class BottomNavItemMain(var title: String, var icon: Int, var screen_route: String) {

    object QuickGame : BottomNavItemMain(
        "Быстрая игра",
        R.drawable.ic_quick_game,
        "quick_game"
    )

    object Profile : BottomNavItemMain(
        "Профиль",
        R.drawable.ic_profile,
        "profile"
    )

    object Friends : BottomNavItemMain(
        "Друзья",
        R.drawable.ic_friends,
        "team"
    )
}