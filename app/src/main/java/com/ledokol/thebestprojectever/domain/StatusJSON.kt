package com.ledokol.thebestprojectever.domain

import com.squareup.moshi.Json

data class StatusJSON(
    @field:Json(name = "android_package_name")
    val androidPackageName : String,
    @field:Json(name = "name")
    val name : String,
    @field:Json(name = "android_category")
    val androidCategory : String,
)