package com.nux.studio.dvor.data.local.profile

import com.nux.studio.dvor.domain.profile.DefaultProfilePic

data class ProfileState(
    var profile: Profile? = null,
    val lastGame: String = "",
    val finish_register: Boolean = false,
    val isLoading: Boolean = false,
    val id_confirmation_phone: String = "",
    val defaultProfilePicsList: List<DefaultProfilePic> = listOf(),
    val verifyErrorMessage: String = ""
)