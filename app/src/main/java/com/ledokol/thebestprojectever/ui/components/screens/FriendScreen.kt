package com.ledokol.thebestprojectever.ui.components.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH3

@Composable
fun FriendScreen(
    viewModel: MainViewModel
){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Icon(
            bitmap = ImageBitmap.imageResource(id = R.drawable.anonymous),
            contentDescription = "Аноним",
            modifier = Modifier
                .heightIn(max = 50.dp)
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.normal_round))),
            tint = Color.Unspecified,
        )
        HeadlineH3(
            text = "Имя пользователя",

        )
    }
}