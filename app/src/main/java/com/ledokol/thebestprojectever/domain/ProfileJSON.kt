package com.ledokol.thebestprojectever.domain

import com.squareup.moshi.Json


data class ProfileJSON(
    @field:Json(name = "username")
    var nickname: String?,
    @field:Json(name = "password")
    var password: String?,
    @field:Json(name = "email")
    var email: String?,
    @field:Json(name = "name")
    var name: String?,
)