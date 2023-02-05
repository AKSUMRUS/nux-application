package com.ledokol.dvor.ui.navigation

import com.ledokol.dvor.R

// Какие экраны в навигации должны быть, класс принимает одно из значений
sealed class BottomNavItemMain(var title: String, var icon: Int, var screen_route: String) {

    object Profile : BottomNavItemMain(
        "Профиль",
        R.drawable.profile_icon_bottomnav,
        "profile"
    )

    object Friends : BottomNavItemMain(
        "Друзья",
        R.drawable.game_icon_bottomnav,
        "team"
    )

    object Notifications : BottomNavItemMain(
        "Уведомления",
        R.drawable.ic_push_ups,
        "notifications"
    )
}