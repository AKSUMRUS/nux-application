package com.ledokol.thebestproject.ui.components.molecules.friend

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.ui.components.atoms.Search
import com.ledokol.thebestproject.ui.components.atoms.buttons.ButtonBorder
import com.ledokol.thebestproject.ui.components.atoms.texts.*

@Composable
fun TitleFriends(
    text: String,
    modifier: Modifier = Modifier,
){

    HeadlineH4(
        text = text,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .padding(top = 20.dp, bottom = 0.dp)
            .then(modifier)
    )


}