package com.ledokol.thebestproject.domain.statistics

import com.squareup.moshi.Json

data class GameSessionStatistics(
    @field:Json(name = "android_package_name")
    val android_package_name: String,
    @field:Json(name = "dt_begin")
    val dt_begin: String,
    @field:Json(name = "dt_end")
    val dt_end: String,
)
