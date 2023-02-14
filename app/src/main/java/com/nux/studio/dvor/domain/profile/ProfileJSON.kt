package com.nux.studio.dvor.domain.profile

import com.squareup.moshi.Json


data class ProfileJSON(
    @field:Json(name = "nickname")
    var nickname: String?,
    @field:Json(name = "name")
    var name: String?,
    @field:Json(name = "password")
    var password: String? = null,
    @field:Json(name = "phone")
    var phone: String?,
    @field:Json(name = "default_profile_pic_id")
    var default_profile_pic_id: String?,
)