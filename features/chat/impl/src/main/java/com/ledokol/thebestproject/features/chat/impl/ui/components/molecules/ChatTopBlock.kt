package com.ledokol.thebestproject.features.chat.impl.ui.components.molecules

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.features.chat.impl.R
import com.nux.studio.dvor.core_ui.atoms.CircleAsyncImageWithBorder

@Composable
fun ChatTopBlock() {
    Row{
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.arrow_left),
            contentDescription = "back arrow",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(25.dp)
        )

        CircleAsyncImageWithBorder(
            url = "https://sun9-6.userapi.com/impg/zmzFaRBkJtt_KwMGd41ARQyNMRxIctDLPD3uCg/U3HSrag1wIw.jpg?size=1035x1280&quality=95&sign=846b0408cc33466822f75ec8a3728431&type=album",
            description = "friend avatar image",
            borderColor = MaterialTheme.colors.secondary,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(bottom = 6.dp, top = 6.dp)
        )
    }
}

@Preview
@Composable
fun ChatTopBlockPreview() {
    ChatTopBlock()
}