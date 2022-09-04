package com.ledokol.thebestproject.ui.components.molecules.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH4
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH5
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH6

@Composable
fun AdditionalBlock(
    text: String,
    openText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                       onClick()
            }
            .background(MaterialTheme.colors.primary)
        ,

    ){
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(15.dp)
        ) {
            HeadlineH5(
                text = text,
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier
            ) {
               HeadlineH6(
                   text = openText,
                   color = MaterialTheme.colors.onBackground,
                   fontWeight = FontWeight.Normal,
                   modifier = Modifier
                       .alignByBaseline()
               )

               Image(
                   imageVector = ImageVector.vectorResource(id = R.drawable.ic_chevron_right),
                   contentDescription = "chevron right",
                   modifier = Modifier
                       .align(CenterVertically)
               )
            }

        }
    }

}