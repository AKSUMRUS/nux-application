package com.ledokol.thebestprojectever.ui.navigation

import android.content.Context
import androidx.compose.ui.res.stringResource
import com.ledokol.thebestprojectever.R

// Какие экраны в навигации должны быть, класс принимает одно из значений
sealed class BottomNavItemMain(var title: String, var icon: Int, var screen_route: String) {

    object QuickGame : BottomNavItemMain(
        "Quick game",
        R.drawable.ic_quick_game,
        "quick_game"
    )

    object Friends : BottomNavItemMain(
        "Friends",
        R.drawable.ic_friends,
        "friends"
    )

    object Profile : BottomNavItemMain(
        "Profile",
        R.drawable.ic_profile,
        "profile"
    )
}