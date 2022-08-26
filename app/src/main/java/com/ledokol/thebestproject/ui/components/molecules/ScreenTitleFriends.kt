package com.ledokol.thebestproject.ui.components.molecules

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScreenTitleFriends(
    name: String,
    description: String? = null,
    onFindFriendClick : () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row{
        ScreenTitle(name, description, modifier)
//        ButtonWithImage(
//            icon = painterResource(id = R.drawable.ic_radar),
//            onClick = onFindFriendClick,
//            modifier = modifier
//        )
    }
}