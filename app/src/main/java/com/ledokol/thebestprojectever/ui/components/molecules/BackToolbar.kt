package com.ledokol.thebestprojectever.ui.components.molecules

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.Body1
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH5
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH6

@Composable
fun BackToolbar() {

    Box(
        modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
    ){
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(start = 20.dp),
            )

            Body1(
                text = stringResource(id = R.string.back).uppercase(),
                modifier = Modifier.padding(start = 10.dp),
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ){
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.star),
                contentDescription = null,
                modifier = Modifier
//                    .align(CenterVertically)
//                    .size(30.dp)
                    .align(CenterVertically),
            )
        }
    }

}