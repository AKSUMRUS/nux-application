package com.ledokol.thebestproject.ui.components.molecules.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH3
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH4

@Composable
fun StatisticsBlock(

) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ,
    ) {
        HeadlineH4(
            text = stringResource(id = R.string.statistics),
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.Bold,
        )

        Box(
            Modifier
                .background(color = MaterialTheme.colors.primary)
                .clip(RoundedCornerShape(7.dp))
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
            ) {
                HeadlineH4(
                    text = "Скоро здесь будет ваша \n" +
                            "игровая статистика!\n" +
                            "Но мы пока её не запрогали :)"
                )
            }
        }
    }
}