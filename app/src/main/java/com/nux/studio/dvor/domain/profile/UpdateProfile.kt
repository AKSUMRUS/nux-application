package com.nux.studio.dvor.domain.profile


data class UpdateProfile(
    val default_profile_pic_id: String? = null,
    val name: String? = null,
    val nickname: String? = null,
)