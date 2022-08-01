package com.ledokol.thebestprojectever.domain.profile

import com.squareup.moshi.Json

data class ConfirmationPhone(
    @field:Json(name = "phone")
    val phone : String,
    @field:Json(name = "reason")
    val reason : String,
)