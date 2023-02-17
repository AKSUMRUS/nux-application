package com.nux.studio.dvor.features.chat.impl.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nux.studio.dvor.features.chat.impl.filters.FilterType
import com.nux.studio.dvor.features.chat.impl.filters.getColor

@Preview
@Composable
private fun FiltersPreview() {
    Filters()
}

@Composable
fun Filters(
    modifier: Modifier = Modifier,
    onFilterClick: ((FilterType) -> Unit)? = null,
) {
    val filterList = listOf(
        FilterType.IN_SEARCH,
        FilterType.IN_GAME,
        FilterType.NOT_ACTIVE,
    )
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        filterList.forEach {
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .height(16.dp)
                    .background(it.getColor())
                    .clickable {
                        if (onFilterClick != null) {
                            onFilterClick(it)
                        }
                    },
                color = Color.White,
                text = "Some textasdfasdf",
                style = TextStyle(
                    fontSize = 10.sp
                ),
            )
        }
    }
}