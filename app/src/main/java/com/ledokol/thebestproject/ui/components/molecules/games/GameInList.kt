package com.ledokol.thebestproject.ui.components.molecules.games

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ledokol.thebestproject.ui.components.atoms.texts.Body1

@Composable
fun GameInList(
    packageName: String,
    name: String,
    icon: String,
    onClick: () -> Unit = {},
    openGame: Boolean = false,
    usageTime: String? = null,
) {

    Column(
        modifier = Modifier,
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(175.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colors.primary)
                .clickable {
                    onClick()
                },
            verticalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                model = icon,
                contentDescription = "GameImage",
                modifier = Modifier
//                .border(5.dp, MaterialTheme.colors.background)
                    .size(100.dp)
                    .align(CenterHorizontally)
                    .padding(10.dp),
            )

            Body1(
                text = name,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            )

        }

        usageTime?.let {
            Body1(
                text = "Играл в эту игру $it минут",
                modifier = Modifier
                    .padding(top = 0.dp, start = 20.dp)
//                    modifier = Modifier.align(Alignment.BottomCenter)
            )
        }


    }
}