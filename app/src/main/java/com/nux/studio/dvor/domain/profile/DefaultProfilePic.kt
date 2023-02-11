package com.nux.studio.dvor.domain.profile

import com.squareup.moshi.Json


data class DefaultProfilePic(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "url")
    val url: String,
)