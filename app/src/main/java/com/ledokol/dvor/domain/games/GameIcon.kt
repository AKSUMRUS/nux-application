package com.ledokol.dvor.domain.games

import okhttp3.MultipartBody

data class GameIcon(
    val package_name: String,
    val icon_preview: MultipartBody.Part
)