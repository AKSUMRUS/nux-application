package com.ledokol.thebestprojectever.domain.games

import com.squareup.moshi.Json

data class StatusJSON(
    @field:Json(name = "android_package_name")
    val android_package_name : String,
    @field:Json(name = "name")
    val name : String,
    @field:Json(name = "android_category")
    val android_category : Int,
)