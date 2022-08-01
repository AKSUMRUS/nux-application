package com.ledokol.thebestprojectever.domain.profile

import com.squareup.moshi.Json


data class LoginJSON(
//    @field:Json(name = "user")
    val phone: String,
//    @field:Json(name = "phone_confirmation")
    val phone_confirmation: ConfirmationPhoneAuth,
)