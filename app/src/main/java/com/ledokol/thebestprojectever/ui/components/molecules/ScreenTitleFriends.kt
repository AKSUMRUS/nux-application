package com.ledokol.thebestprojectever.ui.components.molecules

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.google.common.io.Resources.getResource
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonWithIcon
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonWithImage
import id.zelory.compressor.loadBitmap

@Composable
fun ScreenTitleFriends(
    name: String,
    description: String? = null,
    onFindFriendClick : () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row{
        ScreenTitle(name, description, modifier)
        ButtonWithImage(
            icon = painterResource(id = R.drawable.ic_radar),
            onClick = onFindFriendClick,
            modifier = modifier
        )
    }
}