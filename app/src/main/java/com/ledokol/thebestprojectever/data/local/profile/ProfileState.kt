package com.ledokol.thebestprojectever.data.local.profile

import com.ledokol.thebestprojectever.domain.profile.DefaultProfilePic
import com.ledokol.thebestprojectever.domain.profile.DefaultProfilePicsList

data class ProfileState (
    val profile: Profile? = null,
    val lastGame: String = "",
    val finish_register: Boolean = false,
    val isLoading: Boolean = false,
    val id_confirmation_phone: String = "",
    val defaultProfilePicsList: List<DefaultProfilePic> = listOf(),
    val whenCanVerifyPhone : String = "",
)