package com.ledokol.thebestprojectever.domain.profile

import com.squareup.moshi.Json


data class RegisterJSON(
//    @field:Json(name = "user")
    val user: ProfileJSON,
//    @field:Json(name = "phone_confirmation")
    val phone_confirmation: ConfirmationPhoneAuth,
)