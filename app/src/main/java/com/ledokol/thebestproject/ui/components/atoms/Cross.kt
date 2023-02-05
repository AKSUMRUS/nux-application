package com.ledokol.thebestproject.ui.components.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.R

@Composable
fun Cross(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .then(modifier)
            .clip(RoundedCornerShape(11.dp))
            .clickable { onClick() }
            .background(MaterialTheme.colors.primary)
    ) {
        Icon(
            ImageBitmap.imageResource(id = R.drawable.cross),
            contentDescription = null,
            modifier = Modifier
                .padding(20.dp)
                .size(height = 20.dp, width = 20.dp),
            tint = MaterialTheme.colors.onPrimary,
        )
    }

}
