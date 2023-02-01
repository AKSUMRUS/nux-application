package com.ledokol.thebestproject.domain.profile

import com.squareup.moshi.Json

data class ResponseConfirmationPhone(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "phone")
    val phone: String,
    @field:Json(name = "expiration_dt")
    val expiration_dt: String,
    @field:Json(name = "dt_can_retry_after")
    val dt_can_retry_after: String,
)