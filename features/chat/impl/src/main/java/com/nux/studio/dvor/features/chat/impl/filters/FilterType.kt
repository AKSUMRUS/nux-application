package com.nux.studio.dvor.features.chat.impl.filters

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.nux.studio.dvor.features.chat.impl.R

enum class FilterType {
    IN_SEARCH,
    IN_GAME,
    NOT_ACTIVE
}

@Composable
fun FilterType.getText(): String =
    when (this) {
        FilterType.IN_SEARCH -> stringResource(id = R.string.status_in_search)
        FilterType.IN_GAME -> stringResource(id = R.string.status_in_game)
        FilterType.NOT_ACTIVE -> stringResource(id = R.string.status_not_active)
    }

@Composable
fun FilterType.getColor(): Color =
    when (this) {
        FilterType.IN_SEARCH -> Color(0xFF8358FF)
        FilterType.IN_GAME -> Color(0xFF71C82D)
        FilterType.NOT_ACTIVE -> Color(0xFF6F6F6F)
    }
