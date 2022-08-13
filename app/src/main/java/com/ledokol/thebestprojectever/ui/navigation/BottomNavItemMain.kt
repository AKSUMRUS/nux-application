package com.ledokol.thebestprojectever.ui.navigation

import com.ledokol.thebestprojectever.R

// Какие экраны в навигации должны быть, класс принимает одно из значений
sealed class BottomNavItemMain(var title: String, var icon: Int, var screen_route: String) {

    object QuickGame : BottomNavItemMain(
        "Быстрая игра",
        R.drawable.game_icon_bottomnav,
        "quick_game"
    )

    object Profile : BottomNavItemMain(
        "Профиль",
        R.drawable.home_icon_bottomnav,
        "profile"
    )

    object Friends : BottomNavItemMain(
        "Друзья",
        R.drawable.friends_icon_bottomnav,
        "team"
    )

    object Notifications : BottomNavItemMain(
        "Уведомления",
        R.drawable.ic_push_ups,
        "notifications"
    )
}