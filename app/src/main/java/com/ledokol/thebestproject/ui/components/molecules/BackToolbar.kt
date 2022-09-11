package com.ledokol.thebestproject.ui.components.molecules

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.ui.components.atoms.texts.Body1
import com.ledokol.thebestproject.R

@Composable
fun BackToolbar(
    buttonBackClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .padding(top = 50.dp),
    ){
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 10.dp)
                .clickable(
                    onClick = { buttonBackClick() },
                )
                .padding(10.dp),
        ){
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp),
            )

            Body1(
                text = stringResource(id = R.string.back).uppercase(),
                modifier = Modifier.padding(start = 10.dp),
                color = MaterialTheme.colors.onBackground
            )
        }

        Box(
            contentAlignment = Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Center),
        ){
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.star),
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                ,
            )
        }
    }

}