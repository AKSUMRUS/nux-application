package com.ledokol.thebestproject.domain.profile


data class LoginJSON(
//    @field:Json(name = "user")
    val phone: String,
//    @field:Json(name = "phone_confirmation")
    val phone_confirmation: ConfirmationPhoneAuth,
)