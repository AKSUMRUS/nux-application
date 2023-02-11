package com.nux.studio.dvor.domain.games

import com.squareup.moshi.Json

data class GameJSON(
    @field:Json(name = "android_package_name")
    val android_package_name: String,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "category")
    val category: String,
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "icon_preview")
    val icon_preview: String,
    @field:Json(name = "image_wide")
    val image_wide: String = "",
    @field:Json(name = "icon_large")
    val icon_large: String = "",
)