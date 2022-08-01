package com.ledokol.thebestprojectever.domain.profile

import com.squareup.moshi.Json

data class ConfirmationPhoneAuth(
    @field:Json(name = "id")
    val id : String,
    @field:Json(name = "code")
    val code : String,
)